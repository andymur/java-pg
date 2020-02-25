package com.andymur.pg.pocket.util;

public class Pair<U, V> implements Tuple {

	private final U first;
	private final V second;

	private Pair(final U first, final V second) {
		this.first = first;
		this.second = second;
	}

	public static <U, V> Pair<U, V> of(final U first, final V second) {
		return new Pair<>(first, second);
	}

	public U first() {
		return first;
	}

	public V second() {
		return second;
	}

	@Override
	public int arity() {
		return 2;
	}
}
