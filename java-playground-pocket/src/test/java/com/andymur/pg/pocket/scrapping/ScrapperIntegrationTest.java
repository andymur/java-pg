package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.model.label.Currency;
import com.andymur.pg.pocket.scrapping.currency.CurrencyScrapper;
import com.andymur.pg.pocket.scrapping.model.Rate;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ScrapperIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapperIntegrationTest.class);

    private final Scrapper scrapper = new CurrencyScrapper();
    private final RateHolder rateHolder = new RateHolderImpl(scrapper);

    @Before
    public void setUp() {
    }

    @Test
    public void testScrapping() {

        Optional<Rate> murRub = rateHolder.fetchRate(Currency.MUR, Currency.RUB);
        Optional<Rate> eurUsd = rateHolder.fetchRate(Currency.EUR, Currency.USD);
        Optional<Rate> usdEur = rateHolder.fetchRate(Currency.USD, Currency.EUR);

        Assert.assertTrue(murRub.isPresent());
        Assert.assertTrue(eurUsd.isPresent());
        Assert.assertTrue(usdEur.isPresent());

        LOGGER.info("MURRUB rate: {}", murRub.get());
        LOGGER.info("EURUSD rate: {}", eurUsd);
        LOGGER.info("USDEUR rate: {}", usdEur);

        Assert.assertThat("Reciprocal is ok", eurUsd.get(), CoreMatchers.is(usdEur.get().reciprocal()));
    }
}
