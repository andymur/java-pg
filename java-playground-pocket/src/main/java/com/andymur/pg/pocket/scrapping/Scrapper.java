package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.scrapping.model.Rate;
import com.andymur.pg.pocket.util.Pair;

import java.time.LocalDate;
import java.util.Set;

public interface Scrapper {
    Pair<LocalDate, Set<Rate>> gather();
    Pair<LocalDate, Set<Rate>> gather(LocalDate measurementDate);
}
