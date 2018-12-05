package com.andymur.pg.java.builders;

public class MarketOrderNegotiationDto extends OrderNegotiationDto {
	private String tradingVenue;

	private MarketOrderNegotiationDto(final String ownerId, final String referenceId) {
		super(ownerId, referenceId);
	}
}
