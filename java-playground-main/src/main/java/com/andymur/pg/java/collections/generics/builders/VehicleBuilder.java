package com.andymur.pg.java.collections.generics.builders;

public abstract class VehicleBuilder<T extends VehicleBuilder<T>> extends BaseBuilder<T> {
	protected int wheelNumber;

	public T wheelNumber(final int wheelNumber) {
		this.wheelNumber = wheelNumber;
		return self();
	}

	@Override
	public abstract T self();
}
