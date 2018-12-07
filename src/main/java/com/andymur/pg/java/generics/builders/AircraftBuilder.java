package com.andymur.pg.java.generics.builders;

public abstract class AircraftBuilder<T extends AircraftBuilder<T>> extends BaseBuilder<T> {
	protected int enginesNumber;

	public T enginesNumber(final int enginesNumber) {
		this.enginesNumber = enginesNumber;
		return self();
	}

	@Override
	public abstract T self();
}
