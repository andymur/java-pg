package com.andymur.pg.java.yaml.model.dao;

import java.util.List;

public class WordDescriptionDao {

	private String word;
	private String partOfSpeech;
	private String gender;
	private String plural;
	private String noun;
	private ParticiplesDao participles;
	private ConjugationsDao conjugations;
	private List<String> examples;


	public WordDescriptionDao() {
	}

	public String getWord() {
		return word;
	}

	public void setWord(final String word) {
		this.word = word;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(final String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getPlural() {
		return plural;
	}

	public void setPlural(final String plural) {
		this.plural = plural;
	}

	public String getNoun() {
		return noun;
	}

	public void setNoun(final String noun) {
		this.noun = noun;
	}

	public List<String> getExamples() {
		return examples;
	}

	public void setExamples(final List<String> examples) {
		this.examples = examples;
	}

	public ParticiplesDao getParticiples() {
		return participles;
	}

	public void setParticiples(final ParticiplesDao participles) {
		this.participles = participles;
	}

	public ConjugationsDao getConjugations() {
		return conjugations;
	}

	public void setConjugations(final ConjugationsDao conjugations) {
		this.conjugations = conjugations;
	}

	@Override
	public String toString() {
		return "WordDescriptionDao{" +
				"word='" + word + '\'' +
				", partOfSpeech='" + partOfSpeech + '\'' +
				", gender='" + gender + '\'' +
				", plural='" + plural + '\'' +
				", noun='" + noun + '\'' +
				", participles=" + participles +
				", conjugations=" + conjugations +
				", examples=" + examples +
				'}';
	}
}
