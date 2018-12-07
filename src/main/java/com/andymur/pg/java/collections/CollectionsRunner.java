package com.andymur.pg.java.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CollectionsRunner {

	public static void main(String[] args) {
		/*List<String> p = Arrays.asList("Venus", "Terra", "Pluto");
		List<String> np = new ArrayList<>(p.subList(0, 1)); np.add("Mars");
		System.out.println(np);*/
		Map<String, Integer> cityToPopulation = new HashMap<>();

		cityToPopulation.put("Paris", 2_000_000);
		cityToPopulation.put("Moscow", 12_000_000);

		Function<String, Integer> unknownCityPopulation = cityName -> 0;

		cityToPopulation.computeIfAbsent("Saint Petersburg", unknownCityPopulation);
		cityToPopulation.computeIfAbsent("Moscow", unknownCityPopulation);
		cityToPopulation.computeIfAbsent("Tehran", unknownCityPopulation);

		System.out.println(cityToPopulation);

		List<City> cities = new ArrayList<>(cityToPopulation.size());

		cityToPopulation.forEach((cityName, population) ->
			cities.add(City.of(cityName, population))
		);

		System.out.println(cities);
		cities.sort(Comparator.comparingInt(City::getPopulation).thenComparing(City::getName).reversed());
		System.out.println(cities);
	}

	static class City {
		private final String name;
		private final int population;

		public static City of(final String name, final int population) {
			return new City(name, population);
		}

		public static City  of(final String name) {
			return of(name, 0);
		}

		private City(final String name, final int population) {
			this.name = name;
			this.population = population;
		}

		@Override
		public String toString() {
			return "City{" +
					"name='" + name + '\'' +
					", population=" + population +
					'}';
		}

		public String getName() {
			return name;
		}

		public int getPopulation() {
			return population;
		}
	}
}
