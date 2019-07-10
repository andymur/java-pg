package com.andymur.pg.java.basics;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class NumberFormatRunner {
	public static void main(String[] args) throws ParseException {
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		df.setDecimalFormatSymbols(symbols);
		System.out.println(df.parse("3,4").doubleValue());

		final String regexp = "^\\d*\\.?\\d+|\\d+\\.?\\d*$";
		final String currentText = ",2";

		System.out.println(currentText.matches(regexp));


		System.out.println(String.format("%1$s %1$s %1$s %1$s %1$s %1$s", "Hi"));
	}
}
