package com.andymur.pg.pocket.model;

public enum  Currency implements LabeledMeasurement {
    US_DOLLAR("USD"), EUR("EUR"), ROUBLE("RUB");

    private final String symbol;

    private Currency(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public MeasureUnit getUnit() {
        return MeasureUnit.CCY_UNIT;
    }


}
