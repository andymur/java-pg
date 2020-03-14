package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.model.label.Currency;
import com.andymur.pg.pocket.model.label.base.Label;
import com.andymur.pg.pocket.scrapping.model.Rate;
import com.andymur.pg.pocket.util.Pair;

import java.time.LocalDate;
import java.util.*;

public class RateHolderImpl implements RateHolder {

    private static final Label DEFAULT_LABEL = Currency.USD;

    private final Scrapper scrapper;
    private final Map<LocalDate, Map<Label, List<Rate>>> ratesByDate = new HashMap<>();

    public RateHolderImpl(Scrapper scrapper) {
        this.scrapper = scrapper;
    }

    //TODO: refactor me!
    @Override
    public Optional<Rate> fetchRate(final LocalDate rateDate,
                                    final Label baseSymbol,
                                    final Label quoteSymbol) {
        if (ratesByDate.get(rateDate) == null) {
            prepareDataForDate(rateDate);
        }

        Map<Label, List<Rate>> ratesByDate = this.ratesByDate.getOrDefault(rateDate, Collections.emptyMap());
        Optional<Rate> directRate = ratesByDate.getOrDefault(baseSymbol, Collections.emptyList())
                .stream()
                .filter(rate -> rate.getQuoteSymbol().equals(quoteSymbol))
                .findFirst();

        if (!directRate.isPresent()) {
            Optional<Rate> firstQuote = ratesByDate.getOrDefault(DEFAULT_LABEL, Collections.emptyList())
                    .stream()
                    .filter(rate -> rate.getQuoteSymbol().equals(baseSymbol))
                    .findFirst().map(Rate::reciprocal);

            Optional<Rate> secondQuote = ratesByDate.getOrDefault(DEFAULT_LABEL, Collections.emptyList())
                    .stream()
                    .filter(rate -> rate.getQuoteSymbol().equals(quoteSymbol))
                    .findFirst();

            if (firstQuote.isPresent() && secondQuote.isPresent()) {
                return Optional.of(new Rate.RateBuilder()
                        .baseSymbol(firstQuote.get().getQuoteSymbol())
                        .quoteSymbol(secondQuote.get().getQuoteSymbol())
                        .date(rateDate)
                        .rate(firstQuote.get().getRate().multiply(secondQuote.get().getRate()))
                        .build());
            }
            return Optional.empty();
        }

        return directRate;
    }

    @Override
    public Optional<Rate> fetchRate(Label baseSymbol, Label quoteSymbol) {
        return fetchRate(LocalDate.now(), baseSymbol, quoteSymbol);
    }

    private void prepareDataForDate(final LocalDate date) {
        Pair<LocalDate, Set<Rate>> ratesOfDate = scrapper.gather(date);
        prepareData(date, ratesOfDate.second());
    }

    private void prepareData(final LocalDate date, final Set<Rate> rates) {
        Map<Label, List<Rate>> ratesForLabel = new HashMap<>();

        for (final Rate rate: rates) {
            List<Rate> symbolRates = ratesForLabel.getOrDefault(rate.getBaseSymbol(), new ArrayList<>());

            if (symbolRates.isEmpty()) {
                ratesForLabel.put(rate.getBaseSymbol(), symbolRates);
            }
            symbolRates.add(rate);

            final Rate reciprocalRate = rate.reciprocal();
            List<Rate> reciprocalSymbolRates
                    = ratesForLabel.getOrDefault(reciprocalRate.getBaseSymbol(), new ArrayList<>());
            if (reciprocalSymbolRates.isEmpty()) {
                ratesForLabel.put(reciprocalRate.getBaseSymbol(), reciprocalSymbolRates);
            }
            reciprocalSymbolRates.add(reciprocalRate);
        }

        ratesByDate.put(date, ratesForLabel);
    }
}
