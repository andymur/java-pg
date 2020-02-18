package com.andymur.pg.java.collections.streams.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Country {
	private String name;
	private double population;
	private double area;
	private Set<String> languages;

	public Country(final String name,
				   final double population,
				   final double area,
				   final String language) {
		this(name, population, area, Collections.singleton(language));
	}

	public Country(final String name,
				   final double population,
				   final double area,
				   final Set<String> languages) {
		Objects.requireNonNull(name);
		this.name = name;
		this.population = population;
		this.area = area;
		this.languages = languages;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPopulation(final double population) {
		this.population = population;
	}

	public void setArea(final double area) {
		this.area = area;
	}

	public void setLanguages(final Set<String> languages) {
		this.languages = languages;
	}

	public String getName() {
		return name;
	}

	public double getPopulation() {
		return population;
	}

	public double getArea() {
		return area;
	}

	public Set<String> getLanguages() {
		return languages;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final Country country = (Country) o;

		return name.equals(country.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "Country{" +
				"name='" + name + '\'' +
				", population=" + population +
				", area=" + area +
				", languages=" + languages +
				'}';
	}
}
