package com.andymur.pg.pocket.scrapping.currency;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.andymur.pg.pocket.scrapping.Scrapper;
import com.andymur.pg.pocket.scrapping.model.Currency;
import com.andymur.pg.pocket.scrapping.model.OpenExchangeRatesResponse;
import com.andymur.pg.pocket.scrapping.model.Rate;
import com.andymur.pg.pocket.util.Pair;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrencyScrapper implements Scrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyScrapper.class);
	private static final String PROXY_URI = null;//"http://192.168.4.22:3128/";

	private final Client client;

	public CurrencyScrapper() {
		ClientConfig config = new ClientConfig();

		if (PROXY_URI != null) {
			config.connectorProvider(new ApacheConnectorProvider());
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		}

		client = ClientBuilder.newClient(config);
	}

	@Override
	public Pair<LocalDate, Set<Rate>> gather() {
		return gather(LocalDate.now());
	}

	@Override
	public Pair<LocalDate, Set<Rate>> gather(final LocalDate measurementDate) {
		LOGGER.info(".gather.start; measurementDate={}", measurementDate);
		final OpenExchangeRatesResponse ratesResponse = collectUnderlying();
		final Currency baseCurrency = ratesResponse.getBase();
		final Set<Rate> rates = new HashSet<>();

		for (Map.Entry<Currency, BigDecimal> responseRate: ratesResponse.getRates().entrySet()) {

			rates.add(new Rate.RateBuilder()
					.date(measurementDate)
					.baseSymbol(com.andymur.pg.pocket.model.label.Currency.valueOf(baseCurrency.name()))
					.quoteSymbol(com.andymur.pg.pocket.model.label.Currency.valueOf(responseRate.getKey().name()))
					.rate(responseRate.getValue())
					.build());
		}
		LOGGER.info(".gather.finish; rates = {}", rates);
		return Pair.of(measurementDate, rates);
	}

	private OpenExchangeRatesResponse collectUnderlying() {
		final WebTarget target = client.target(OpenExchangeRatesResponse.OPEN_EXCHANGE_RATES_API_URL);
		final Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
		return invocationBuilder.get(OpenExchangeRatesResponse.class);
	}
}
