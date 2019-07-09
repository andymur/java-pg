package com.andymur.pg.java.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollectionRunner {

	public static void main(String[] args) {
		/*Supplier<Boolean> supTrue = () -> true;

		List<Boolean> allTrue = new ArrayList<>(Arrays.asList(true, true, true));
		List<Boolean> someTrue = new ArrayList<>(Arrays.asList(true, true, false));
		List<Boolean> allFalse = new ArrayList<>(Arrays.asList(false, false, false));


		System.out.println("ALL MATCH ALL TRUE: " + allTrue.stream().allMatch(aBoolean -> aBoolean));
		System.out.println("ALL MATCH SOME TRUE: " + someTrue.stream().allMatch(aBoolean -> aBoolean));
		System.out.println("ALL MATCH ALL FALSE: " + allFalse.stream().allMatch(aBoolean -> aBoolean));

		System.out.println("=====================");

		System.out.println("ANY MATCH ALL TRUE: " + allTrue.stream().anyMatch(aBoolean -> aBoolean));
		System.out.println("ANY MATCH SOME TRUE: " + someTrue.stream().anyMatch(aBoolean -> aBoolean));
		System.out.println("ANY MATCH ALL FALSE: " + allFalse.stream().anyMatch(aBoolean -> aBoolean));

		System.out.println(Boolean.parseBoolean("true"));*/

		List<Boolean> cityOrCountry = Arrays.asList(true, false, true);

		final List<String> result = cityOrCountry.stream()
				.map(StreamCollectionRunner::cityOrCountry)
				.flatMap(Collection::stream).collect(Collectors.toList());

		System.out.println(result);
	}

	private static Set<String> cityOrCountry(boolean isCity) {
		if (isCity) {
			return city();
		} else {
			Set<String> result = new HashSet<>();
			result.addAll(country());
			result.addAll(city());
			return result;
			//return Stream.concat(country().stream(), city().stream()).collect(Collectors.toSet());
		}
	}

	private static Set<String> city() {
		return Collections.singleton("CI" + UUID.randomUUID().toString());
	}

	private static Set<String> country() {
		return Collections.singleton("CO" + UUID.randomUUID().toString());
	}
}
