package com.andymur.pg.java.yaml.model.dao;

import com.andymur.pg.java.yaml.model.Conjugation;

public class TenseConjugationDao {

	private ConjugationDao singular;
	private ConjugationDao plural;

	public TenseConjugationDao() {
	}

	public void setSingular(final ConjugationDao singular) {
		this.singular = singular;
	}

	public void setPlural(final ConjugationDao plural) {
		this.plural = plural;
	}

	public ConjugationDao getSingular() {
		return singular;
	}

	public ConjugationDao getPlural() {
		return plural;
	}

	@Override
	public String toString() {
		return "TenseConjugationDao{" +
				"singular=" + singular +
				", plural=" + plural +
				'}';
	}
}
