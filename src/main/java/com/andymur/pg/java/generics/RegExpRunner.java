package com.andymur.pg.java.generics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RegExpRunner {
	public static void main(String[] args) {
		/*final String numeric = "(\\d|\\.|-)";

		if (!"s".matches(numeric)) {
			throw new IllegalArgumentException("Boom");
		}*/

		Double.valueOf("2.22");

		BigInteger bigInteger = new BigInteger("12313");

		System.out.println(bigInteger.toString());



		/*if (parameterT instanceof NumericT) {
			textField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
				final String numericSymbols = "(\\d|\\.|-)";
				if (!keyEvent.getCharacter().matches(numericSymbols)) {
					keyEvent.consume();
				}
			});
		}*/

		List<City> cities = Arrays.asList(City.of("Paris", null), City.of("Prague", "... is here"),
				City.of("Saint Petersburg", "???"));


		final String motto = cities.stream()
				.filter(city -> city.name.startsWith("P"))
				.filter(city -> city.motto != null)
				.map(City::getMotto)
				.findFirst().orElse("");

		System.out.println(motto);

		String payload = "Product;Group Id;External Id;Legal entity;Action;Currency 1;Currency 2;Notional Currency;Notional amount 1;Notional amount 2;CF Effective Date;Maturity Date;Limit rate;Stop rate;Expiration;Expiration date;CF Fixing date 1;CF Fixing date 2;CF Fixing;Bank basket;CF OCO Leg;CF Editable;Complex trade component ID;Trading capacity;Investment decision maker is natural person;Investment decision maker;Order Uploader;Order Uploader is natural person;MTF;Fee Type;Fee Currency;Fee Value;Custodian\\\\nSpot;;;MT.Req2;Buy;EUR;USD;USD;878787;;08.10.2019;;;;GTC;;;;;;;TRUE;;;;;;;;per million;eur;80001;MT.Bank3";

		System.out.println(payload.replace("\\\\n", "\n"));
	}

	static class City {
		final String name;
		final String motto;

		public City(final String name,
					final String motto) {
			this.name = name;
			this.motto = motto;
		}

		public static City of(final String name,
							  final String motto) {
			return new City(name, motto);
		}

		public static City of(final String name) {
			return new City(name, null);
		}

		public String getMotto() {
			return motto;
		}
	}
}
