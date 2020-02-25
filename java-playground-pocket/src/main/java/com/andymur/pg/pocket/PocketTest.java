package com.andymur.pg.pocket;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

class PocketTest {
	// stocks & bonds & etfs? check https://www.alphavantage.co/documentation/
	// https://www.coingecko.com/en/api
	// https://docs.openexchangerates.org/docs/
	private final static String OPEN_EXCHANGE_RATES_API_KEY = "96b23fe216f54f4d842396347a40ac60";

	private static final String CCY_EXCHANGE_API_URL = "https://api.exchangeratesapi.io/2019-04-01";
	private static final String OPEN_EXCHANGE_RATES_API_URL = "https://openexchangerates.org/api/latest.json?app_id=" + OPEN_EXCHANGE_RATES_API_KEY;

	private static final String PROXY_URI = "http://192.168.4.22:3128/";

	public static void main(String[] args) {

		ClientConfig config = new ClientConfig();

		if (PROXY_URI != null) {
			config.connectorProvider(new ApacheConnectorProvider());
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		}

		Client client = ClientBuilder.newClient(config);
		//askAndPrintCurrencyRates(client);
		//askAndPrintOpenExchangeCurrencyRates(client);
	}

	private static void askAndPrintCurrencyRates(final Client client) {
		final WebTarget target = client.target(CCY_EXCHANGE_API_URL);
		final Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
		final ExchangeResponseRates ratesResponse = invocationBuilder.get(ExchangeResponseRates.class);
		System.out.println(ratesResponse);
	}

	private static void askAndPrintOpenExchangeCurrencyRates(final Client client) {
		final WebTarget target = client.target(OPEN_EXCHANGE_RATES_API_URL);
		final Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
		final OpenExchangeRatesResponse openExchangeRatesResponse = invocationBuilder.get(OpenExchangeRatesResponse.class);
		System.out.println(openExchangeRatesResponse);
	}

	public enum Currency {
		AED, AFN, ALL, AMD, ANG, AOA, ARS, AUD, AWG, AZN,
		BAM, BBD, BDT, BGN, BHD, BIF, BMD, BND, BOB, BRL,
		BSD, BTC, BTN, BWP, BYN, BZD, CAD, CDF, CHF, CLF,
		CLP, CNH, CNY, COP, CRC, CUC, CUP, CVE, CZK, DJF,
		DKK, DOP, DZD, EGP, ERN, ETB, EUR, FJD, FKP, GBP,
		GEL, GGP, GHS, GIP, GMD, GNF, GTQ, GYD, HKD, HNL,
		HRK, HTG, HUF, IDR, ILS, IMP, INR, IQD, IRR, ISK,
		JEP, JMD, JOD, JPY, KES, KGS, KHR, KMF, KPW, KRW,
		KWD, KYD, KZT, LAK, LBP, LKR, LRD, LSL, LYD, MAD,
		MDL, MGA, MKD, MMK, MNT, MOP, MRO, MRU, MUR, MVR,
		MWK, MXN, MYR, MZN, NAD, NGN, NIO, NOK, NPR, NZD,
		OMR, PAB, PEN, PGK, PHP, PKR, PLN, PYG, QAR, RON,
		RSD, RUB, RWF, SAR, SBD, SCR, SDG, SEK, SGD, SHP,
		SLL, SOS, SRD, SSP, STD, STN, SVC, SYP, SZL, THB,
		TJS, TMT, TND, TOP, TRY, TTD, TWD, TZS, UAH, UGX,
		USD, UYU, UZS, VEF, VES, VND, VUV, WST, XAF, XAG,
		XAU, XCD, XDR, XOF, XPD, XPF, XPT, YER, ZAR, ZMW,
		ZWL
	}

	public static class OpenExchangeRatesResponse {
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

	public static class ExchangeResponseRates {
		private Map<Currency, BigDecimal> rates;
		private Currency base;
		private Date date;

		public ExchangeResponseRates() {
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
}