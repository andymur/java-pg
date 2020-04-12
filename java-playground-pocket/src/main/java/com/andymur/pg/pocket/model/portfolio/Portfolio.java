package com.andymur.pg.pocket.model.portfolio;

import com.andymur.pg.pocket.model.asset.base.Asset;
import com.andymur.pg.pocket.model.asset.base.AssetValue;
import com.andymur.pg.pocket.model.label.base.LabeledMeasurement;

import java.math.MathContext;
import java.math.RoundingMode;

public interface Portfolio {

    int DEFAULT_PRECISION = 2;
    MathContext DEFAULT_MATH_CONTEXT = new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_EVEN);

    /**
     * Calculates portfolio value in asset's value on TOD date
     *
     * @param measurement in which we value the portfolio
     * @return
     */
    AssetValue calculatePortfolio(LabeledMeasurement measurement);

    /**
     * Adds asset to portfolio
     * @param asset to be added
     */
    void addAsset(Asset asset);

    /**
     * Removes asset from portfolio
     * @param asset to be removed
     */
    void removeAsset(Asset asset);
}
