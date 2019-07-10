package com.andymur.pg.java.builders;

public abstract class OrderNegotiationDto extends NegotiationDto {
	private final String referenceId;

	protected OrderNegotiationDto(final String ownerId,
								final String referenceId) {
		super(ownerId);
		this.referenceId = referenceId;
	}

	public static abstract class Builder<DTO extends OrderNegotiationDto, T extends Builder<DTO, T>> extends NegotiationDto.Builder<DTO, T> {
		private String referenceId;

		public T referenceId(final String referenceId) {
			this.referenceId = referenceId;
			return self();
		}

		public String getReferenceId() {
			return referenceId;
		}

		@Override
		public T fromDto(final DTO negot) {
			return super.fromDto(negot)
					.referenceId(negot.getReferenceId());
		}

		@Override
		public abstract DTO build();
	}

	@Override
	public String toString() {
		return "OrderNegotiationDto{" +
				"referenceId='" + referenceId + "'" +
				", ownerId=" + getOwnerId() +
				"}";
	}

	public String getReferenceId() {
		return referenceId;
	}
}
