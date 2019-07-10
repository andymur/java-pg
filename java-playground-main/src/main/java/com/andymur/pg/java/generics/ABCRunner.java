package com.andymur.pg.java.generics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ABCRunner {
	public static void main(String[] args) {
		Map<String, List<String>> countryToCities = new HashMap<>();
		countryToCities.put("F", Arrays.asList("M", "N"));
		countryToCities.put("G", Arrays.asList("F", "K"));

		System.out.println(countryToCities.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));

		Set<City> citySet = new HashSet<>(Arrays.asList(City.of("Paris", 1000000),
				City.of("London", 10000000)));

		//Map<String, Integer> cityToPopulation = citySet.stream().collect(Collectors.toMap(City::getName, City::getPopulation));

		Map<String, Integer> cityToPopulation = citySet.stream().map(City::getName)
				.collect(Collectors.toMap(Function.identity(), k -> k.length()));

		System.out.println(cityToPopulation);
		System.out.println(cityToPopulation.values());

		final String cons = "LEG_ORDER_REFERENCE_ID";
		final String leg = "LEG_ORDER_REFERENCE_ID_213";

		Matcher m = Pattern.compile(cons + "_(\\d+)").matcher(leg);

		if (m.find()) {
			System.out.println(m.group(1));
		}

		try {
			System.out.println("T");
			return;
		} finally {
			System.out.println("F");
		}
	}

	static class City {

		final String name;
		final int population;

		private City(final String name, final int population) {
			this.name = name;
			this.population = population;
		}

		public String getName() {
			return name;
		}

		public int getPopulation() {
			return population;
		}

		static City of(String name, int population) {
			return new City(name, population);
		}
	}

}
