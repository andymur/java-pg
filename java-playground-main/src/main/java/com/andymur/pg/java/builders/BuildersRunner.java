package com.andymur.pg.java.builders;

public class BuildersRunner {
	public static void main(String[] args) {
		final String ORDER_TYPE = "MARKET";

		OrderNegotiationDto.Builder builder;

		switch (ORDER_TYPE) {
			case "MARKET":
				builder = new MarketOrderDto.Builder().ownerId("providerB").tradingVenue("VENUE");
				break;
			case "LIMIT":
				builder = new LimitOrderDto.Builder().limit(3.0);
				break;
			default:
				builder = new MarketOrderDto.Builder();
		}

		builder.referenceId("EMSO-X");

		OrderNegotiationDto orderNegotiationDto = builder.build();
		System.out.println(orderNegotiationDto);

		MarketOrderDto limitOrderDto = new MarketOrderDto.Builder().fromDto((MarketOrderDto) orderNegotiationDto).ownerId("providerA").build();

		System.out.println(limitOrderDto);

		/*
		System.out.println(orderNegotiationDto);*/
	}
}
