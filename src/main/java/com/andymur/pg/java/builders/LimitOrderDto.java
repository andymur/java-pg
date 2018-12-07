package com.andymur.pg.java.builders;

public class LimitOrderDto extends OrderNegotiationDto {

	private final Double limit;

	protected LimitOrderDto(final String ownerId,
							final String referenceId,
							final Double limit) {
		super(ownerId, referenceId);
		this.limit = limit;
	}

	@Override
	public Builder createBuilder() {
		return new Builder();
	}

	public static class Builder extends OrderNegotiationDto.Builder<LimitOrderDto, Builder> {
		private Double limit;

		public Builder limit(final Double limit) {
			this.limit = limit;
			return this;
		}

		@Override
		protected Builder self() {
			return this;
		}

		@Override
		public LimitOrderDto build() {
			return new LimitOrderDto(getOwnerId(), getReferenceId(), limit);
		}

		@Override
		public Builder fromDto(final LimitOrderDto negot) {
			return super.fromDto(negot).limit(negot.getLimit());
		}
	}

	@Override
	public String toString() {
		return "LimitOrderDto{" +
				"referenceId='" + getReferenceId() + "'" +
				", limit='" + getLimit() + "'" +
				", ownerId=" + getOwnerId() +
				"}";
	}

	public Double getLimit() {
		return limit;
	}
}
