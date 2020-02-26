package com.andymur.pg.pocket.model.asset.base;

import com.andymur.pg.pocket.model.label.LabeledMeasurement;

public interface Asset extends LabeledMeasurement {
    AssetValue getValue();
    AssetType getType();
}
