package com.andymur.pg.pocket.model;

import com.andymur.pg.pocket.model.asset.base.Asset;
import com.andymur.pg.pocket.model.asset.base.AssetValue;

public interface Portfolio {
    AssetValue calculatePortfolio(Asset asset);
}
