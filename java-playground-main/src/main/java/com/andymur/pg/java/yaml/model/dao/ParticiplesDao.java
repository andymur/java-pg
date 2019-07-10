package com.andymur.pg.java.yaml.model.dao;

public class ParticiplesDao {
	private String present;
	private String perfect;

	public ParticiplesDao() {
	}

	public void setPresent(final String present) {
		this.present = present;
	}

	public void setPerfect(final String perfect) {
		this.perfect = perfect;
	}

	public String getPresent() {
		return present;
	}

	public String getPerfect() {
		return perfect;
	}

	@Override
	public String toString() {
		return "ParticiplesDao{" +
				"present='" + present + '\'' +
				", perfect='" + perfect + '\'' +
				'}';
	}
}
