package com.andymur.pg.pocket.model;

import java.math.BigDecimal;

public interface FutureValuedAsset extends Asset {
    Asset futureAsset();
}
