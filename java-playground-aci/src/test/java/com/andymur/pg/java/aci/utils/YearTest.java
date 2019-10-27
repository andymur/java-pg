package com.andymur.pg.java.aci.utils;

import org.junit.Assert;
import org.junit.Test;

public class YearTest {

    @Test
    public void testFourHundredsYearIsLeap() {
        Assert.assertTrue("2000 year is leap year", Year.of(2000).isLeap());
    }

    @Test
    public void testHundredYearIsLeap() {
        Assert.assertFalse("1900 year is non-leap year", Year.of(1900).isLeap());
    }

    @Test
    public void testYearIsNotLeap() {
        Assert.assertFalse("2019 year is non-leap year", Year.of(2019).isLeap());
    }

    @Test
    public void testYearIsLeap() {
        Assert.assertTrue("2020 year is leap year", Year.of(2020).isLeap());
    }
}
