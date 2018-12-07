package com.andymur.pg.java.builders;

public class RfsNegotiationDto extends NegotiationDto {
	private final long timeout;

	private RfsNegotiationDto(final String ownerId, final long timeout) {
		super(ownerId);
		this.timeout = timeout;
	}

	@Override
	public Builder createBuilder() {
		return null;
	}

	public static class Builder extends NegotiationDto.Builder<RfsNegotiationDto, Builder> {

		private long timeout;

		public Builder timeout(final long timeout) {
			this.timeout = timeout;
			return this;
		}

		@Override
		protected Builder self() {
			return this;
		}

		@Override
		public RfsNegotiationDto build() {
			return new RfsNegotiationDto(ownerId, timeout);
		}

		@Override
		public Builder fromDto(final RfsNegotiationDto negot) {
			return super.fromDto(negot)
					.timeout(negot.timeout);
		}
	}

	@Override
	public String toString() {
		return "RfsNegotiationDto{" +
				"timeout='" + getTimeout() + "'" +
				", ownerId=" + getOwnerId() +
				"}";
	}

	public long getTimeout() {
		return timeout;
	}
}
