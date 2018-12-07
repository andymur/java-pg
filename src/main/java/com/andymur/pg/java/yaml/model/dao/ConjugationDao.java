package com.andymur.pg.java.yaml.model.dao;

public class ConjugationDao {
	private String firstPerson;
	private String secondPerson;
	private String thirdPerson;

	public ConjugationDao() {
	}

	public void setFirstPerson(final String firstPerson) {
		this.firstPerson = firstPerson;
	}

	public void setSecondPerson(final String secondPerson) {
		this.secondPerson = secondPerson;
	}

	public void setThirdPerson(final String thirdPerson) {
		this.thirdPerson = thirdPerson;
	}

	public String getFirstPerson() {
		return firstPerson;
	}

	public String getSecondPerson() {
		return secondPerson;
	}

	public String getThirdPerson() {
		return thirdPerson;
	}

	@Override
	public String toString() {
		return "ConjugationDao{" +
				"firstPerson='" + firstPerson + '\'' +
				", secondPerson='" + secondPerson + '\'' +
				", thirdPerson='" + thirdPerson + '\'' +
				'}';
	}
}
