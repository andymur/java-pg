package com.andymur.pg.pocket.model;

public enum Metal implements LabeledMeasurement {

    GOLD("XAU"), SILVER("XAG");

    private final String symbol;

    private Metal(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public MeasureUnit getUnit() {
        return MeasureUnit.TROY_OZ;
    }
}
