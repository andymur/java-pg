package com.andymur.pg.java.xstream;

import com.andymur.pg.java.xstream.model.City;
import com.andymur.pg.java.xstream.model.Country;
import com.thoughtworks.xstream.XStream;

public class XStreamRunner {

	private final static String MOSCOW_SERIALIZED = "<com.andymur.pg.java.xstream.model.City><name>Moscow</name><population>12000000.0</population><area>0.0</area><latitude>60.0</latitude><longitude>60.0</longitude><country><name>Russia</name><population>14.6E7</population><area>17.0E6</area><languages class=\"singleton-set\"><string>Russian</string></languages></country></com.andymur.pg.java.xstream.model.City>";

	public static void main(String[] args) {
		Country germany = new Country("Deutschland", 82_000_000, 0, "Deutsch");
		City frankfurt = new City("Frankfurt am Main", 760_000, 0, 50, 30, germany);

		XStream xstream = new XStream();
		System.out.println(xstream.toXML(frankfurt));

		final City moscow = (City) xstream.fromXML(MOSCOW_SERIALIZED, City.class);
		System.out.println(moscow);
	}
}
