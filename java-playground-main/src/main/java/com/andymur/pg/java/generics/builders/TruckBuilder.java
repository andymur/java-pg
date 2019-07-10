package com.andymur.pg.java.generics.builders;

public class TruckBuilder extends VehicleBuilder<TruckBuilder> {
	private int tonnage;

	public TruckBuilder tonnage(final int tonnage) {
		this.tonnage = tonnage;
		return self();
	}

	@Override
	public TruckBuilder self() {
		return this;
	}
}
