package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.model.label.base.Label;
import com.andymur.pg.pocket.scrapping.model.Rate;
import com.andymur.pg.pocket.util.Pair;
import com.andymur.pg.pocket.util.Tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class ScrapperMock implements Scrapper {

    private static final LocalDate TODAY = LocalDate.now();
    private Set<RateTuple> mockedData;

    public ScrapperMock(final Set<RateTuple> mockedData) {
        this.mockedData = mockedData;
    }

    @Override
    public Pair<LocalDate, Set<Rate>> gather() {
        return Pair.of(TODAY, getRates());
    }

    @Override
    public Pair<LocalDate, Set<Rate>> gather(LocalDate measurementDate) {
        return Pair.of(measurementDate, getRates());
    }

    public Set<Rate> getRates() {
        return mockedData.stream().map(RateTuple::toRate).collect(Collectors.toSet());
    }

    static class RateTuple implements Tuple {

        private final LocalDate date;
        private final Label baseSymbol;
        private final Label quoteSymbol;
        private final BigDecimal rate;

        public RateTuple(final LocalDate date,
                         final Label baseSymbol,
                         final Label quoteSymbol,
                         final BigDecimal rate) {
            this.date = date;
            this.baseSymbol = baseSymbol;
            this.quoteSymbol = quoteSymbol;
            this.rate = rate;
        }

        public Rate toRate() {
            return new Rate.RateBuilder()
                    .date(this.date)
                    .baseSymbol(baseSymbol)
                    .quoteSymbol(quoteSymbol)
                    .rate(rate)
                    .build();
        }

        @Override
        public int arity() {
            return 4;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RateTuple rateTuple = (RateTuple) o;

            if (!date.equals(rateTuple.date)) return false;
            if (!baseSymbol.equals(rateTuple.baseSymbol)) return false;
            return quoteSymbol.equals(rateTuple.quoteSymbol);

        }

        @Override
        public int hashCode() {
            int result = date.hashCode();
            result = 31 * result + baseSymbol.hashCode();
            result = 31 * result + quoteSymbol.hashCode();
            return result;
        }
    }
}
