package com.andymur.pg.pocket.model.asset;

import com.andymur.pg.pocket.model.asset.base.Asset;
import com.andymur.pg.pocket.model.asset.base.AssetType;
import com.andymur.pg.pocket.model.asset.base.AssetValue;
import com.andymur.pg.pocket.model.label.Security;
import com.andymur.pg.pocket.model.label.base.MeasureUnit;

public class SecurityDeposit implements Asset {

    private Security security;

    @Override
    public AssetValue getValue() {
        return null;
    }

    @Override
    public AssetType getType() {
        return AssetType.SECURITY;
    }

    @Override
    public MeasureUnit getUnit() {
        return security.getUnit();
    }

    @Override
    public String getSymbol() {
        return security.getSymbol();
    }
}
