package com.andymur.pg.pocket.scrapping.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.andymur.pg.pocket.model.label.Label;

public class Rate {

	private final LocalDate date;
	private final Label baseSymbol;
	private final Label quoteSymbol;
	private final BigDecimal rate;

	private Rate(final LocalDate date,
						final Label baseSymbol,
						final Label quoteSymbol,
						final BigDecimal rate) {
		this.date = date;
		this.baseSymbol = baseSymbol;
		this.quoteSymbol = quoteSymbol;
		this.rate = rate;
	}

	public static class RateBuilder {
		private LocalDate date;
		private Label baseSymbol;
		private Label quoteSymbol;
		private BigDecimal rate;

		public RateBuilder date(final LocalDate date) {
			this.date = date;
			return this;
		}

		public RateBuilder baseSymbol(final Label baseSymbol) {
			this.baseSymbol = baseSymbol;
			return this;
		}

		public RateBuilder quoteSymbol(final Label quoteSymbol) {
			this.quoteSymbol = quoteSymbol;
			return this;
		}

		public RateBuilder rate(final BigDecimal rate) {
			this.rate = rate;
			return this;
		}

		public Rate build() {
			Objects.requireNonNull(date);
			Objects.requireNonNull(baseSymbol);
			Objects.requireNonNull(quoteSymbol);
			Objects.requireNonNull(rate);

			return new Rate(date, baseSymbol, quoteSymbol, rate);
		}
	}

	@Override
	public String toString() {
		return "Rate{" +
				"date=" + date +
				", baseSymbol=" + baseSymbol +
				", quoteSymbol=" + quoteSymbol +
				", rate=" + rate +
				'}';
	}
}
