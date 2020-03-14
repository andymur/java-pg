package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.model.label.Currency;
import com.andymur.pg.pocket.scrapping.model.Rate;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class RateHolderTest {
    private static final MathContext MATH_CONTEXT = new MathContext(2, RoundingMode.HALF_EVEN);
    private RateHolder rateHolder;

    @Test
    public void testCrossRateCalculation() {

        Set<ScrapperMock.RateTuple> rates = new HashSet<>();
        rates.add(new ScrapperMock.RateTuple(LocalDate.now(),
                Currency.USD, Currency.EUR, BigDecimal.ONE.divide(new BigDecimal("1.2"), MATH_CONTEXT)));
        rates.add(new ScrapperMock.RateTuple(LocalDate.now(),
                        Currency.USD, Currency.RUB, new BigDecimal("65")));
        ScrapperMock scrapper = new ScrapperMock(rates);

        rateHolder = new RateHolderImpl(scrapper);

        Rate rate = rateHolder.fetchRate(Currency.EUR, Currency.RUB).orElseThrow(RuntimeException::new);
        Assert.assertThat(rate.getRate(), CoreMatchers.is(new BigDecimal("78.0")));
    }
}