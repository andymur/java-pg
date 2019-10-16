package com.andymur.pg.java.aci.utils;

import org.junit.Assert;
import org.junit.Test;

public class YearTest {

    @Test
    public void testYearIsLeap() {
        Assert.assertTrue(Year.of(2000).isLeap());
    }

    @Test
    public void testYearIsNotLeap() {
        Assert.assertFalse(Year.of(2019).isLeap());
    }
}
