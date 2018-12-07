package com.andymur.pg.java.generics.builders;

public class JetFighterBuilder extends AircraftBuilder<JetFighterBuilder> {
	public int racketsNumber;

	public JetFighterBuilder racketsNumber(final int racketsNumber) {
		this.racketsNumber = racketsNumber;
		return self();
	}

	@Override
	public JetFighterBuilder self() {
		return this;
	}
}
