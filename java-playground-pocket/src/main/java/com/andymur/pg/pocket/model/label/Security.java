package com.andymur.pg.pocket.model.label;

import com.andymur.pg.pocket.model.label.base.LabeledMeasurement;
import com.andymur.pg.pocket.model.label.base.MeasureUnit;

import static com.andymur.pg.pocket.model.label.base.MeasureUnit.BASE_UNIT;

// any security paper
public interface Security extends LabeledMeasurement {
    default MeasureUnit getUnit() {
        return BASE_UNIT;
    }
}
