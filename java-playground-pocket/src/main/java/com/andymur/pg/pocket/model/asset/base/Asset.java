package com.andymur.pg.pocket.model.asset.base;

import com.andymur.pg.pocket.model.label.base.LabeledMeasurement;

public interface Asset extends LabeledMeasurement {
    AssetValue getValue();
    AssetType getType();
}
