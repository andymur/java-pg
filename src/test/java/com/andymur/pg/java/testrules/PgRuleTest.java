package com.andymur.pg.java.testrules;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * Simple example of JUnit rules usage.
 *
 * Here we have the rule which tests whether test method contains 'Softly'.
 *
 * When test method doesn't contain this as a part of its name rule throws an exception
 *
 */
public class PgRuleTest {
    @Rule
    public PgRule pgRule = new PgRule();

    @Test
    public void testMeSoftly() {
    }

    @Ignore
    @Test
    public void testMeHard() {

    }
}
