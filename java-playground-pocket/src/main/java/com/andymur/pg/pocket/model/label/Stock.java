package com.andymur.pg.pocket.model.label;

public enum Stock implements Security {

    MICROSOFT("MSF");

    private final String ticker;

    Stock(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public String getSymbol() {
        return ticker;
    }
}
