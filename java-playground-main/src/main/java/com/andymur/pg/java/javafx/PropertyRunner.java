package com.andymur.pg.java.javafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PropertyRunner {
	public static void main(String[] args) {
		StringProperty greet = new SimpleStringProperty("Hi");
		StringProperty a = new SimpleStringProperty();

		greet.bind(a);


		greet.addListener(
				(observable, oldValue, newValue) ->
					System.out.println("Changed!")
		);
		a.setValue("A");
		System.out.println(greet);
		System.out.println(greet.get());
	}
}
