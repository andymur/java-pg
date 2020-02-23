package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.scrapping.model.Measurement;
import com.andymur.pg.pocket.util.Pair;

import java.time.LocalDate;
import java.util.Set;

public interface Scraper {
    Pair<LocalDate, Set<Measurement>> gather();
    Pair<LocalDate, Set<Measurement>> gather(LocalDate measurementDate);
}
