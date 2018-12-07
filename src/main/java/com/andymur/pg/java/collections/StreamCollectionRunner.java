package com.andymur.pg.java.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class StreamCollectionRunner {

	public static void main(String[] args) {
		Supplier<Boolean> supTrue = () -> true;

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

		System.out.println(Boolean.parseBoolean("true"));
	}
}
