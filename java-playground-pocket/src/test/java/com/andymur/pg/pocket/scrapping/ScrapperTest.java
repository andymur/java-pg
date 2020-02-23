package com.andymur.pg.pocket.scrapping


import com.andymur.pg.pocket.scrapping.model.Measurement;
import com.andymur.pg.pocket.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Set;

class ScrapperTest {

    private Scrapper scrapper;

    @Before
    public void setUp() {
        scrapper = new CurrencyScrapper();
    }

    @Test
    public void testScrapper() {
        Pair<LocalDate, Set<Measurement>> result = scrapper.gather();
        Assert.assertEquals("Default date should be today",
        LocalDate.now(), result.first());
    }
}
