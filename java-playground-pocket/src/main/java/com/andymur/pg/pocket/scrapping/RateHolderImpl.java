package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.model.label.base.Label;
import com.andymur.pg.pocket.scrapping.model.Rate;
import com.andymur.pg.pocket.util.Pair;

import java.time.LocalDate;
import java.util.*;

public class RateHolderImpl implements RateHolder {
    private final Scrapper scrapper;
    private final Map<LocalDate, Map<Label, List<Rate>>> ratesByDate = new HashMap<>();

    public RateHolderImpl(Scrapper scrapper) {
        this.scrapper = scrapper;
    }

    @Override
    public Rate fetchRate(LocalDate rateDate, Label baseSymbol, Label quoteSymbol) {
        return null;
    }

    @Override
    public Rate fetchRate(Label baseSymbol, Label quoteSymbol) {
        return null;
    }

    private void prepareDataForDate(final LocalDate date) {
        Pair<LocalDate, Set<Rate>> ratesOfDate = scrapper.gather(date);
        prepareData(date, ratesOfDate.second());
    }

    private void prepareData(final LocalDate date, final Set<Rate> rates) {
        Map<Label, List<Rate>> ratesForLabel = new HashMap<>();

        for (final Rate rate: rates) {
            List<Rate> symbolRates = ratesForLabel.getOrDefault(rate.getBaseSymbol(), Collections.emptyList());
            symbolRates.add(rate);

            final Rate reciprocalRate = rate.reciprocal();
            List<Rate> reciprocalSymbolRates
                    = ratesForLabel.getOrDefault(reciprocalRate.getBaseSymbol(), Collections.emptyList());
            reciprocalSymbolRates.add(reciprocalRate);
        }

        ratesByDate.put(date, ratesForLabel);
    }
}
