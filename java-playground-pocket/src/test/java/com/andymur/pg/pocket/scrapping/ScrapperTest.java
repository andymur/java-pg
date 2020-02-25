package com.andymur.pg.pocket.scrapping;

import java.time.LocalDate;
import java.util.Set;

import com.andymur.pg.pocket.scrapping.currency.CurrencyScrapper;
import com.andymur.pg.pocket.scrapping.model.Rate;
import com.andymur.pg.pocket.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScrapperTest {
	private Scrapper scrapper;

	@Before
	public void setUp() {
		this.scrapper = new CurrencyScrapper();
	}

	@Test
	public void testScrapping() {
		final Pair<LocalDate, Set<Rate>> measurements = scrapper.gather();
		Assert.assertEquals("Default date is today", LocalDate.now(), measurements.first());
		Assert.assertEquals("We have some measurements by now", 171, measurements.second().size());
	}
}
