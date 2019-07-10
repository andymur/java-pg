package com.andymur.pg.java.concurrency;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class TimeRunner {
	public static void main(String[] args) {
		boolean timeIsOut = false;
		long startQueryingLog = System.currentTimeMillis();
		System.out.println(new Time(System.currentTimeMillis()));
		while (!timeIsOut) {
			timeIsOut = TimeUnit.SECONDS.convert(System.currentTimeMillis() - startQueryingLog, TimeUnit.MILLISECONDS) >= 10;
		}
		System.out.println(new Time(System.currentTimeMillis()));
	}
}
