package com.andymur.pg.pocket.model.asset.base;

import com.andymur.pg.pocket.model.label.base.LabeledMeasurement;

import java.math.BigDecimal;

public class AssetValue {
    private final LabeledMeasurement measurement;
    private final BigDecimal value;

    public AssetValue(LabeledMeasurement measurement, BigDecimal value) {
        this.measurement = measurement;
        this.value = value;
    }
}
