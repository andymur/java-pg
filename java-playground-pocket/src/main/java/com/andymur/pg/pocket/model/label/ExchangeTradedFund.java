package com.andymur.pg.pocket.model.label;

import com.andymur.pg.pocket.model.label.base.LabeledMeasurement;
import com.andymur.pg.pocket.model.label.base.MeasureUnit;

import static com.andymur.pg.pocket.model.label.base.MeasureUnit.BASE_UNIT;

public enum ExchangeTradedFund implements Security {

    VANGUARD_SP500("VANGUARD_SP500", "IE00B3XXRP09");

    private final String symbol;
    private final String isin;


    ExchangeTradedFund(final String symbol,
                       final String isin) {
        this.symbol = symbol;
        this.isin = isin;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    public String getIsin() {
        return isin;
    }
}
