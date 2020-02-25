package com.andymur.pg.pocket.scrapping.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ExchangeRatesResponse {
	private Map<Currency, BigDecimal> rates;
	private Currency base;
	private Date date;

	public ExchangeRatesResponse() {
	}

	public Map<Currency, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(final Map<Currency, BigDecimal> rates) {
		this.rates = rates;
	}

	public Currency getBase() {
		return base;
	}

	public void setBase(final Currency base) {
		this.base = base;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "RatesResponse{" +
				"rates=" + rates +
				", base='" + base + '\'' +
				", date='" + date + '\'' +
				'}';
	}
}
