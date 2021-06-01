package com.andymur.pg.java.collections.generics.builders;

public class CarBuilder extends VehicleBuilder<CarBuilder> {
	private int doorNumber;

	public CarBuilder doorNumber(final int doorNumber) {
		this.doorNumber = doorNumber;
		return self();
	}

	@Override
	public CarBuilder self() {
		return this;
	}
}
