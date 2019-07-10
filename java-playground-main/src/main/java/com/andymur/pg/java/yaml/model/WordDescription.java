package com.andymur.pg.java.yaml.model;

import java.util.ArrayList;
import java.util.List;

import com.andymur.pg.java.yaml.model.dao.WordDescriptionDao;

public class WordDescription {

	private String word;
	private final PartOfSpeech partOfSpeech;
	private final Gender gender;
	private String plural;
	private String noun;
	private List<String> examples;

	public WordDescription(final WordDescriptionDao wordDescriptionDao) {
		this.word = wordDescriptionDao.getWord();

		if (wordDescriptionDao.getPartOfSpeech() != null) {
			this.partOfSpeech = PartOfSpeech.valueOf(wordDescriptionDao.getPartOfSpeech().toUpperCase());
		} else {
			this.partOfSpeech = null;
		}

		if (wordDescriptionDao.getGender() != null) {
			this.gender = Gender.valueOf(wordDescriptionDao.getGender().toUpperCase());
		} else {
			this.gender = null;
		}

		this.plural = wordDescriptionDao.getPlural();
		this.noun = wordDescriptionDao.getNoun();
		this.examples = new ArrayList<>(wordDescriptionDao.getExamples());
	}

	public String getWord() {
		return word;
	}

	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}

	public Gender getGender() {
		return gender;
	}

	public String getPlural() {
		return plural;
	}

	public String getNoun() {
		return noun;
	}

	public List<String> getExamples() {
		return examples;
	}

	@Override
	public String toString() {
		return "WordDescription{" +
				"word='" + word + '\'' +
				", partOfSpeech=" + partOfSpeech +
				", gender=" + gender +
				", plural='" + plural + '\'' +
				", noun='" + noun + '\'' +
				", examples=" + examples +
				'}';
	}
}
