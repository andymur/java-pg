package com.andymur.pg.java.collections.generics.builders;

public class BaseBuilderRunner {

	public static void main(String[] args) {
		VehicleBuilder<CarBuilder> vehicleBuilder = new CarBuilder();
		vehicleBuilder.id("").doorNumber(2).wheelNumber(2);

	}
}
