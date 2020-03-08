package com.andymur.pg.pocket.scrapping;

import com.andymur.pg.pocket.model.label.base.Label;
import com.andymur.pg.pocket.scrapping.model.Rate;

import java.time.LocalDate;

public interface RateHolder {
    /**
     * Fetches rate for particular date
     * @param rateDate
     * @param baseSymbol
     * @param quoteSymbol
     * @return
     */
    Rate fetchRate(LocalDate rateDate, Label baseSymbol, Label quoteSymbol);

    /**
     * Fetches rate for today
     * @param baseSymbol
     * @param quoteSymbol
     * @return
     */
    Rate fetchRate(Label baseSymbol, Label quoteSymbol);
}
