package com.andymur.pg.java.yaml;

import java.util.List;

public class WordDescription {

	private String word;
	private String partOfSpeech;
	private String gender;
	private String plural;
	private String noun;
	private List<String> examples;

	public WordDescription() {
	}

	public WordDescription(final String word, final String partOfSpeech, final String gender, final String plural, final String noun, final List<String> examples) {
		this.word = word;
		this.partOfSpeech = partOfSpeech;
		this.gender = gender;
		this.plural = plural;
		this.noun = noun;
		this.examples = examples;
	}

	@Override
	public String toString() {
		return "WordDescription{" +
				"word='" + word + '\'' +
				", partOfSpeech='" + partOfSpeech + '\'' +
				", gender='" + gender + '\'' +
				", plural='" + plural + '\'' +
				", examples=" + examples +
				'}';
	}

	public String getNoun() {
		return noun;
	}

	public void setNoun(final String noun) {
		this.noun = noun;
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

	public List<String> getExamples() {
		return examples;
	}

	public void setExamples(final List<String> examples) {
		this.examples = examples;
	}
}
