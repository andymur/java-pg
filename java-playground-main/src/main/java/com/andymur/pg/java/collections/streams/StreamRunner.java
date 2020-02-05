package com.andymur.pg.java.collections.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.andymur.pg.java.collections.streams.model.City;
import com.andymur.pg.java.collections.streams.model.Country;

public class StreamRunner {

	public static void main(String[] args) {

		Country germany = new Country("Germany", 83_000_000, 0, "German");
		Country russia = new Country("Russia", 147_000_000, 17_000_000, "Russian");

		final List<City> cities = buildCities();
		final Map<Country, List<City>> citiesByCountry = cities.stream().collect(Collectors.groupingBy(City::getCountry));

		assert citiesByCountry.keySet().size() == 2;
		assert citiesByCountry.get(germany).size() == 2;
		assert citiesByCountry.get(russia).size() == 3;

		List<City> nTopCitiesByPopulation = getNTopCitiesByPopulation(2, cities);
		String mostPopulatedCity = nTopCitiesByPopulation.get(0).getName();
		assertIt("Moscow should be the most populated city but is " + mostPopulatedCity, mostPopulatedCity.equals("Moscow"));

		nTopCitiesByPopulation = getNTopCitiesByPopulationStream(2, cities);
		mostPopulatedCity = nTopCitiesByPopulation.get(0).getName();
		assertIt("Moscow should be the most populated city but is " + mostPopulatedCity, mostPopulatedCity.equals("Moscow"));


		/* reducing */

		final Optional<City> city = cityWithBiggestPopulation(cities);
		assertIt("Moscow should be the most populated city but is " + city.get(), city.get().getName().equals("Moscow"));

		/* grouping */


	}

	public static List<City> buildCities() {
		Country germany = new Country("Germany", 83_000_000, 0, "German");
		Country russia = new Country("Russia", 147_000_000, 17_000_000, "Russian");

		return Arrays.asList(
				new City("Frankfurt", 700_000, 0, 0, 0, germany),
				new City("Moscow", 12_000_000, 0, 0, 0, russia),
				new City("Hamburg", 1_700_000, 0, 0, 0, germany),
				new City("Saint Petersburg", 5_200_000, 0, 0, 0, russia),
				new City("Voronezh", 900_000, 0, 0, 0, russia)
		);
	}

	private static List<City> getNTopCitiesByPopulation(int n, Collection<City> cities) {
		SortedSet<City> sortedCities = new TreeSet<>((o1, o2) -> o1.getPopulation() - o2.getPopulation() < 0 ? 1 : - 1);

		sortedCities.addAll(cities);
		return new ArrayList<>(sortedCities).subList(0, 2);
	}

	private static List<City> getNTopCitiesByPopulationStream(int n, Collection<City> cities) {
		return cities.stream()
				.sorted((o1, o2) -> o1.getPopulation() - o2.getPopulation() < 0 ? 1 : - 1)
				.limit(n).collect(Collectors.toList());
	}

	private static Optional<City> cityWithBiggestPopulation(Collection<City> cities) {
		return cities.stream().reduce((c1, c2) -> c1.getPopulation() > c2.getPopulation() ? c1 : c2);
	}

	/*private double populationSum(int n, Collection<City> cities) {
		Arrays.stream(cities.toArray()).sorted()
	}*/

	private static void assertIt(final String message, final boolean condition) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}
