package com.andymur.pg.pocket.model.portfolio;

import com.andymur.pg.pocket.model.asset.base.Asset;
import com.andymur.pg.pocket.model.asset.base.AssetValue;
import com.andymur.pg.pocket.model.label.base.LabeledMeasurement;
import com.andymur.pg.pocket.scrapping.RateHolder;
import com.andymur.pg.pocket.scrapping.model.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PortfolioImpl implements Portfolio {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioImpl.class);

    private final List<Asset> assets = new ArrayList<>();
    private final RateHolder rateHolder;

    public PortfolioImpl(RateHolder rateHolder) {
        this.rateHolder = rateHolder;
    }

    @Override
    public AssetValue calculatePortfolio(LabeledMeasurement measurement) {
        BigDecimal result = BigDecimal.ZERO;

        for (Asset asset : assets) {
            Optional<Rate> rate = rateHolder.fetchRate(measurement, asset.getValue().getMeasurement());
            if (!rate.isPresent()) {
                LOGGER.error("Rate is not presented, portfolio cannot be calculated! base: {}, quote: {}",
                        measurement.getSymbol(), asset.getSymbol());

                throw new IllegalStateException(String.format("Rate is not presented! base: %s, quote: %s",
                        measurement.getSymbol(), asset.getSymbol()));
            }
            BigDecimal measuredAssetValue = asset.getValue().getValue().multiply(rate.get().getRate(), DEFAULT_MATH_CONTEXT);
            result = result.add(measuredAssetValue, DEFAULT_MATH_CONTEXT);
        }

        return new AssetValue(measurement, result);
    }

    @Override
    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    @Override
    public void removeAsset(Asset asset) {
        assets.remove(asset);
    }
}
