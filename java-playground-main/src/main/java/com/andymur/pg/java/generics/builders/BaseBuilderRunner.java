package com.andymur.pg.java.generics.builders;

public class BaseBuilderRunner {

	public static void main(String[] args) {
		VehicleBuilder vehicleBuilder = new CarBuilder();
		((CarBuilder) vehicleBuilder.id("")).doorNumber(2).wheelNumber(2);

	}
}
