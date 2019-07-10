package com.andymur.pg.java.basics;

import java.lang.reflect.Field;

public class StringTest {

	public static void main(String[] args) {
		String a = "abc";
		String b = "abc";
		try {
			Field value = a.getClass().getDeclaredField("value");
			value.setAccessible(true);
			char[] val = (char[]) value.get(a);
			val[1] = 'd';
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println(b);
		System.out.println(~-1);
	}
}