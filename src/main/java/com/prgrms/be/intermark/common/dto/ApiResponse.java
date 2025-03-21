package com.prgrms.be.intermark.common.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

	private int code;
	private HttpStatus httpStatus;
	private String message;
	private T data;

	public ApiResponse(HttpStatus httpStatus, String message, T data) {
		this.code = httpStatus.value();
		this.httpStatus = httpStatus;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
		return new ApiResponse<>(httpStatus, message, data);
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
		return of(httpStatus, httpStatus.name(), data);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.name(), data);
	}

	public static <T> ApiResponse<T> created(T data) {
		return new ApiResponse<>(HttpStatus.CREATED, HttpStatus.CREATED.name(), data);
	}

}
