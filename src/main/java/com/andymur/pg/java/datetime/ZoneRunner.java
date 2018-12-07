package com.andymur.pg.java.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ZoneRunner {
	public static void main(String[] args) {
		LocalDateTime localDateTime = LocalDateTime.now();

		System.out.println(convertToGmt(localDateTime));
		System.out.println(convertToLocalGmt(localDateTime));
		System.out.println(formatted(convertToGmt(localDateTime)));
		/*
		ZoneId zoneId = ZoneId.of("Europe/Berlin");
		ZoneId gmtZone = ZoneId.of("GMT");


		ZonedDateTime localZoneDateTime = localDateTime.atZone(ZoneId.systemDefault());
		ZonedDateTime gmtZoneDateTime = localZoneDateTime.withZoneSameInstant(gmtZone);



		System.out.println(zoneId);
		System.out.println(gmtZone);
		System.out.println(localDateTime.atZone(zoneId));
		System.out.println(localDateTime.atZone(gmtZone).toLocalDateTime());
		System.out.println(localDateTime.atZone(zoneId).getClass().getName());

		ZonedDateTime zdt = ZonedDateTime.of(localDateTime, gmtZone);
		System.out.println(zdt.toOffsetDateTime());
		System.out.println(zdt);
		System.out.println(LocalDateTime.now(gmtZone));


		System.out.println("==============================");
		System.out.println(gmtZoneDateTime);
		System.out.println(gmtZoneDateTime.toLocalDateTime());
		System.out.println("==============================");
		*/

	}

	private static ZonedDateTime convertToGmt(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("GMT"));
	}

	private static LocalDateTime convertToLocalGmt(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("GMT")).toLocalDateTime();
	}

	private static String formatted(ZonedDateTime dateTime) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd.MM.uuuu HH:mm", Locale.ENGLISH);
		return formatter.format(dateTime);
	}

}
