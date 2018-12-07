package com.andymur.pg.java.collections;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;

public class OptionalsRunner {

	public static void main(String[] args) {

		Optional<City> unknownCity = Optional.empty();

		Optional<City> paris = Optional.of(new City("Paris"));
		Optional<City> london = Optional.of(new City("London"));

		Optional<String> result = Stream.of(unknownCity)
				.findFirst()
				.orElse(Optional.empty())
				.map(City::getName);

		System.out.println(result);

		result = Stream.of(paris, london)
				.findFirst()
				.orElse(Optional.empty())
				.map(City::getName);

		System.out.println(result);

		result = Stream.of(london, paris)
				.findFirst()
				.orElse(Optional.empty())
				.map(City::getName);

		System.out.println(result);

		Optional<City> city = Stream.of(unknownCity).findFirst().orElse(Optional.empty());

		result = Stream.of(london, paris)
				.flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
				.findFirst().map(City::getName);

		System.out.println(result);

		result = Stream.of(paris, london)
				.flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
				.findFirst().map(City::getName);

		System.out.println(result);

		result = Stream.of(unknownCity)
				.flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
				.findFirst().map(City::getName);

		System.out.println(result);

		Stream<City> cityStream = Stream.of(paris, london)
				.flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty());

		List<String> americanCityNames = Arrays.asList("NY", "SF");
		List<String> europeanCityNames = Arrays.asList("SP", "RM");

		Optional<String> cityName = Stream.of(paris, london)
				.flatMap(o -> o.isPresent() ? Stream.of(o.get().getName()) : Stream.empty())
				.findFirst();

		Optional<String> cityName2 = Stream.of(unknownCity, london)
				//.flatMap(o -> o.isPresent() ? Stream.of(o.get().getName()) : Stream.empty())
				//.flatMap(o -> o.map(o1 -> Stream.of(o1.getName())).orElse(Stream.empty()))
				.flatMap(o -> o.map(c -> Stream.of(c.getName())).orElse(Stream.empty()))
				.findFirst();

		System.out.println(cityName2);


		List<String> collect = Stream.of(americanCityNames, europeanCityNames)
				.flatMap(List::stream).collect(Collectors.toList());

		System.out.println(collect);

		ListIterator<String> cityIterator = collect.listIterator(collect.size());

		while (cityIterator.hasPrevious()) {
			System.out.println(cityIterator.previous());
		}

		List<String> continentNames = Arrays.asList("Eurasia", "America", "Australia");

		Set<String> cityNames = Stream.concat(americanCityNames.stream(),
				europeanCityNames.stream()).collect(Collectors.toSet());

		System.out.println(cityNames);

		System.out.println(cityNames.stream().allMatch(name -> name.length() == 2));

		City nullCity = new City(null);

		System.out.println(Arrays.stream(new City[] {nullCity}).map(City::getName).filter(Objects::nonNull).findFirst().orElse(null));
	}

	static class City {
		private final String name;

		public City(final String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}
