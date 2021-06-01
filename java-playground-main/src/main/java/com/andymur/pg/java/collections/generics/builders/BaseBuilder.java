package com.andymur.pg.java.collections.generics.builders;

public abstract class BaseBuilder<T extends BaseBuilder<T>> {
	protected String id;

	public T id(final String id) {
		this.id = id;
		return self();
	}

	public abstract T self();
}
