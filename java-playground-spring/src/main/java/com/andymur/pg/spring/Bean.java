package com.andymur.pg.spring;

public class Bean {
	private String name;

	public Bean(final String name) {
		this.name = name;
	}

	public void sayHi() {
		System.out.println("Hi from " + name);
	}
}
