package com.andymur.pg.spring;

public enum ExternalInterfaceType {

	ORDERAPI_CSV("ORDERAPI_CSV"),
	ORDERAPI_XML("ORDERAPI_XML"),
	ORDERAPI_FIX("ORDERAPI_FIX");

	private final String shortName;

	ExternalInterfaceType(final String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", shortName, name());
	}
}