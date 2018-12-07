package com.andymur.pg.java.reflection;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ReflectionRunner {

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		City saintP = new City("SaintP", BigDecimal.ONE);

		Field rating = saintP.getClass().getDeclaredField("rating");
		rating.setAccessible(true);
		rating.set(saintP, new BigInteger("10"));

		System.out.println(saintP);
	}

	public static class City {
		private String name;
		private BigDecimal rating;

		public City(final String name, final BigDecimal rating) {
			this.name = name;
			this.rating = rating;
		}

		@Override
		public String toString() {
			return "City{" +
					"name='" + name + '\'' +
					", rating=" + rating +
					'}';
		}
	}
}