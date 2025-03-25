package com.prgrms.be.intermark.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
	private ApiStatus status;
	private String error;
	private T data;

	public static <T> ResponseDTO<T> success(T data) {
		return new ResponseDTO<>(ApiStatus.SUCCESS, null, data);
	}

	public static ResponseDTO<Void> success() {
		return new ResponseDTO<>(ApiStatus.SUCCESS, null, null);
	}
}
