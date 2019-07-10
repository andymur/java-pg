package com.andymur.pg.java.yaml.model.dao;

public class ConjugationsDao {

	private MoodDao indicative;
	private MoodDao conjunctive;
	private MoodDao imperative;

	public ConjugationsDao() {
	}

	public void setIndicative(final MoodDao indicative) {
		this.indicative = indicative;
	}

	public void setConjunctive(final MoodDao conjunctive) {
		this.conjunctive = conjunctive;
	}

	public void setImperative(final MoodDao imperative) {
		this.imperative = imperative;
	}

	@Override
	public String toString() {
		return "ConjugationsDao{" +
				"indicative=" + indicative +
				", conjunctive=" + conjunctive +
				", imperative=" + imperative +
				'}';
	}
}
