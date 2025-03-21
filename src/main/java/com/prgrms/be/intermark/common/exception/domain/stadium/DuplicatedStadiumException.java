package com.prgrms.be.intermark.common.exception.domain.stadium;

public class DuplicatedStadiumException extends RuntimeException {
	private final String field;
	private final String value;

	public DuplicatedStadiumException(String field, String value) {
		super("duplicate value for field : " + field + ", value : " + value);
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}
}
