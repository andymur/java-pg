package com.andymur.pg.java.xstream.model;

public class City {

	private String name;
	private double population;
	private double area;
	private double latitude;
	private double longitude;
	private Country country;

	public City(final String name,
				final double population,
				final double area,
				final double latitude,
				final double longitude,
				final Country country) {
		this.name = name;
		this.population = population;
		this.area = area;
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
	}

	@Override
	public String toString() {
		return "City{" +
				"name='" + name + '\'' +
				", population=" + population +
				", area=" + area +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", country=" + country +
				'}';
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

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}
}
