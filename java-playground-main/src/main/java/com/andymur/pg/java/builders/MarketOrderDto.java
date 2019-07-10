package com.andymur.pg.java.builders;

public class MarketOrderDto extends OrderNegotiationDto {
	private final String tradingVenue;

	protected MarketOrderDto(final String ownerId, final String referenceId, final String tradingVenue) {
		super(ownerId, referenceId);
		this.tradingVenue = tradingVenue;
	}

	@Override
	public Builder createBuilder() {
		return null;
	}

	public static class Builder extends OrderNegotiationDto.Builder<MarketOrderDto, Builder> {
		private String tradingVenue;

		public Builder tradingVenue(final String tradingVenue) {
			this.tradingVenue = tradingVenue;
			return this;
		}

		@Override
		protected Builder self() {
			return this;
		}

		@Override
		public MarketOrderDto build() {
			return new MarketOrderDto(getOwnerId(), getReferenceId(), tradingVenue);
		}

		@Override
		public Builder fromDto(final MarketOrderDto negot) {
			return super.fromDto(negot)
					.tradingVenue(negot.getTradingVenue());
		}
	}

	@Override
	public String toString() {
		return "MarketOrderDto{" +
				"referenceId='" + getReferenceId() + "'" +
				", tradingVenue='" + getTradingVenue() + "'" +
				", ownerId=" + getOwnerId() +
				"}";
	}

	public String getTradingVenue() {
		return tradingVenue;
	}
}
