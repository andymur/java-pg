package com.andymur.pg.java.aci.utils;

import java.time.LocalDate;

import com.andymur.pg.java.aci.TermPeriodType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TermPeriodCalculatorTest {

	private static final TermPeriodCalculator CALCULATOR = TermPeriodCalculator.of();

	// tests for actual
	@Test
	public void testActualTermCalculation() {

		long termDaysNumber = CALCULATOR.calculateTermPeriodInDays(
				LocalDate.of(2019, 3, 1),
				LocalDate.of(2019, 3, 31),
				TermPeriodType.ACTUAL
		);

		Assert.assertEquals("Actual number of days between 31st of March and 1st of March should be 30",
				30, termDaysNumber);
	}

	@Test
	public void testActualTermCalculationForHugePeriod() {
		long termDaysNumber = CALCULATOR.calculateTermPeriodInDays(
				LocalDate.of(1985, 10, 23),
				LocalDate.of(2019, 10, 23),
				TermPeriodType.ACTUAL
		);
		Assert.assertEquals("Actual number of days between 23rd of October 1985 and 23rd of October 2019 is 12418",
				12418, termDaysNumber);
	}

	@Test
	public void testDaysSinceYearBeginningCalculation() {
		long daysNumber = CALCULATOR.calculateDaysSinceBeginningOfTheYear(LocalDate.of(2019, 1, 10));

		Assert.assertEquals("Actual number of days betwee 10th of January and 1st of January should be 9",
				9, daysNumber);
	}

	@Test
	public void testActualTermCalculationForTheWholeMonth() {

		long termDaysNumber = CALCULATOR.calculateTermPeriodInDays(
				LocalDate.of(2019, 3, 1),
				LocalDate.of(2019, 4, 1),
				TermPeriodType.ACTUAL
		);

		Assert.assertEquals("Actual number of days between 1st of April and 1st of March should be 31",
				31, termDaysNumber);
	}

	// tests for 30 method
	@Test
	public void test30TermCalculation() {
		long termDaysNumber = CALCULATOR.calculateTermPeriodInDays(
				LocalDate.of(2019, 3, 1),
				LocalDate.of(2019, 3, 31),
				TermPeriodType._30
		);

		Assert.assertEquals("By 30 method number of days between 31st of March and 1st of March should be 30",
				30, termDaysNumber);
	}

	@Test
	public void test30TermCalculationForTheWholeMonth() {
		long termDaysNumber = CALCULATOR.calculateTermPeriodInDays(
				LocalDate.of(2019, 3, 1),
				LocalDate.of(2019, 4, 1),
				TermPeriodType._30
		);

		Assert.assertEquals("By 30 method number of days between 1st of April and 1st of March should be 30",
				30, termDaysNumber);
	}

	@Test
	public void test30TermCalculationForDifferentMonthWithinYear() {

		long termDaysNumber = CALCULATOR.calculateTermPeriodInDays(
				LocalDate.of(2019, 3, 1),
				LocalDate.of(2019, 4, 5),
				TermPeriodType._30
		);

		Assert.assertEquals("By 30 method number of days between 5th of April and 1st of March should be 34",
				34, termDaysNumber);
	}

	// tests for 30E method
	@Test
	public void test30ETermCalculation31stShouldBeTreatedAs30th() {
		long termDaysNumber = CALCULATOR.calculateTermPeriodInDays(
				LocalDate.of(2019, 3, 1),
				LocalDate.of(2019, 3, 31),
				TermPeriodType._30E
		);

		Assert.assertEquals("By 30 method number of days between 31st of March and 1st of March should be 29",
				29, termDaysNumber);
	}
}