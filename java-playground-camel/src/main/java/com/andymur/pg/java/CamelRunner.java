package com.andymur.pg.java;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.jetty.JettyHttpComponent;
import org.apache.camel.component.jetty9.JettyHttpComponent9;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelRunner {

	private static final String JETTY_URI = "jetty:https://aa.usno.navy.mil/cgi-bin/aa_rstablew.pl?ID=AA&year=2018&task=0&state=WA&place=Seattlei";

	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setTracing(false);

		JettyHttpComponent jettyComponent = new JettyHttpComponent9();
		HttpComponent httpComponent = new HttpComponent();

		org.apache.camel.component.http4.HttpComponent httpComponent4 = new org.apache.camel.component.http4.HttpComponent();

		camelContext.addComponent("jetty", jettyComponent);
		camelContext.addComponent("http", httpComponent4);

		final RouteBuilder routeBuilder = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				//from("direct:start").to("jetty:http://www.lib.ru/KIPLING/kim.txt");
				/*from("direct:start")
						.log(LoggingLevel.INFO,"hey just accessed something on the web")
						.setHeader(Exchange.HTTP_METHOD, constant("GET"))
						//.to("jetty:http://www.lib.ru/KIPLING/kim.txt")
						.toD("http://www.lib.ru/KIPLING/kim.txt")
						//.process(new LogProcessor())
						.log(LoggingLevel.INFO,"hey just accessed something on the web")
						.to("file:/tmp/kim.txt");*/
				from("direct:start").to(JETTY_URI)
						.log(LoggingLevel.INFO, "${body}").to("file:/tmp/kim.txt");
				//from("direct:start") .setHeader(Exchange.HTTP_METHOD, constant("GET")).to("ahc:http://www.google.com") .to("mock:results");
			}
		};

		try {
			camelContext.addRoutes(routeBuilder);
			camelContext.start();

			ProducerTemplate template = camelContext.createProducerTemplate();
			template.sendBody("direct:start", "Message body");


		} finally {
			Thread.sleep(TimeUnit.SECONDS.toMillis(10));
			camelContext.stop();
		}
	}
}
