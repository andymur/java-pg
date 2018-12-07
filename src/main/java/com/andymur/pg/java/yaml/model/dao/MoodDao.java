package com.andymur.pg.java.yaml.model.dao;

public class MoodDao {
	private TenseConjugationDao present;
	private TenseConjugationDao past;
	private TenseConjugationDao perfect;

	public MoodDao() {
	}

	public void setPresent(final TenseConjugationDao present) {
		this.present = present;
	}

	public void setPast(final TenseConjugationDao past) {
		this.past = past;
	}

	public void setPerfect(final TenseConjugationDao perfect) {
		this.perfect = perfect;
	}

	public TenseConjugationDao getPresent() {
		return present;
	}

	public TenseConjugationDao getPast() {
		return past;
	}

	public TenseConjugationDao getPerfect() {
		return perfect;
	}

	@Override
	public String toString() {
		return "MoodDao{" +
				"present=" + present +
				", past=" + past +
				", perfect=" + perfect +
				'}';
	}
}
