package com.andymur.pg.java.aci;

public enum BasePeriod {
	ACTUAL(0),
	_365(365),
	_360(360);

	private final int numberOfDays;

	private BasePeriod(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}
}
