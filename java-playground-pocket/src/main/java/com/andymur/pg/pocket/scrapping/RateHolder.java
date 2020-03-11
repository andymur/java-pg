package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.model.label.base.Label;
import com.andymur.pg.pocket.scrapping.model.Rate;

import java.time.LocalDate;
import java.util.Optional;

public interface RateHolder {
    /**
     * Fetches rate for particular date
     * @param rateDate
     * @param baseSymbol
     * @param quoteSymbol
     * @return
     */
    Optional<Rate> fetchRate(LocalDate rateDate, Label baseSymbol, Label quoteSymbol);

    /**
     * Fetches rate for today
     * @param baseSymbol
     * @param quoteSymbol
     * @return
     */
    Optional<Rate> fetchRate(Label baseSymbol, Label quoteSymbol);
}
