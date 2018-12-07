package com.andymur.pg.java.builders;

public abstract class NegotiationDto {
	private String ownerId;

	protected NegotiationDto(final String ownerId) {
		this.ownerId = ownerId;
	}

	public static abstract class Builder<DTO extends NegotiationDto, T extends Builder<DTO, T>> {
		protected String ownerId;

		T ownerId(final String ownerId) {
			this.ownerId = ownerId;
			return self();
		}

		protected abstract T self();

		public abstract DTO build();

		String getOwnerId() {
			return ownerId;
		}

		public T fromDto(DTO dto) {
			ownerId(dto.getOwnerId());
			return self();
		}
	}

	public abstract <DTO extends NegotiationDto, T extends Builder<DTO, T>> Builder<DTO, T> createBuilder();

	public String getOwnerId() {
		return ownerId;
	}
}
