package com.prgrms.be.intermark.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
	private ApiStatus status;
	private String error;
	private T data;
}
