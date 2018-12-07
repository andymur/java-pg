package com.andymur.pg.java.yaml.model;

import java.time.Period;

public enum PersonalPronoun {

	I(Person.FIRST, Number.SINGULAR);

	private final Person person;
	private final Number number;

	PersonalPronoun(Person person, Number number) {
		this.person = person;
		this.number = number;
	}
}
