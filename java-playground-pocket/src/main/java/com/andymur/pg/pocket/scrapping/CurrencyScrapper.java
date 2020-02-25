package com.andymur.pg.pocket.scrapping;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import com.andymur.pg.pocket.scrapping.model.Measurement;
import com.andymur.pg.pocket.util.Pair;

public class CurrencyScrapper implements Scrapper {
	@Override
	public Pair<LocalDate, Set<Measurement>> gather() {
		return gather(LocalDate.now());
	}

	@Override
	public Pair<LocalDate, Set<Measurement>> gather(final LocalDate measurementDate) {
		return Pair.of(measurementDate, Collections.emptySet());
	}
}
