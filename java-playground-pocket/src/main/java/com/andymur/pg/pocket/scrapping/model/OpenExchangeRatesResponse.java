package com.andymur.pg.pocket.scrapping.model;

import java.math.BigDecimal;
import java.util.Map;

public class OpenExchangeRatesResponse {

	public static final String OPEN_EXCHANGE_RATES_API_KEY = "96b23fe216f54f4d842396347a40ac60";
	public static final String OPEN_EXCHANGE_RATES_API_URL = "https://openexchangerates.org/api/latest.json?app_id=" + OPEN_EXCHANGE_RATES_API_KEY;

	private long timestamp;
	private String disclaimer;
	private String license;
	private Currency base;
	private Map<Currency, BigDecimal> rates;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(final String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(final String license) {
		this.license = license;
	}

	public Currency getBase() {
		return base;
	}

	public void setBase(final Currency base) {
		this.base = base;
	}

	public Map<Currency, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(final Map<Currency, BigDecimal> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "OpenExchangeRatesResponse{" +
				"timestamp=" + timestamp +
				", disclaimer='" + disclaimer + '\'' +
				", license='" + license + '\'' +
				", base='" + base + '\'' +
				", rates=" + rates +
				'}';
	}
}
