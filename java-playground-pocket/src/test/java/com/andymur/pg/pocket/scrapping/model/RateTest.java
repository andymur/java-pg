package com.andymur.pg.pocket.scrapping.model;

import com.andymur.pg.pocket.model.label.Currency;
import com.andymur.pg.pocket.model.label.base.Label;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class RateTest {

    @Test
    public void testReciprocal() {
        Rate directQuote = new Rate.RateBuilder()
                .date(LocalDate.now())
                .baseSymbol(Currency.EUR)
                .quoteSymbol(Currency.USD)
                .rate(new BigDecimal("1.2"))
                .build();

        Rate reciprocalQuote = directQuote.reciprocal();

        Assert.assertThat(reciprocalQuote.getDate(), CoreMatchers.is(LocalDate.now()));
        Assert.assertThat(reciprocalQuote.getBaseSymbol(), CoreMatchers.is(Currency.USD));
        Assert.assertThat(reciprocalQuote.getQuoteSymbol(), CoreMatchers.is(Currency.EUR));
        Assert.assertThat(reciprocalQuote.getRate(), CoreMatchers.is(new BigDecimal("0.83")));
    }
}