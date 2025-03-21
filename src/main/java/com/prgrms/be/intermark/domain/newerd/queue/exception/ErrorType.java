package com.prgrms.be.intermark.domain.newerd.queue.exception;

import org.springframework.boot.logging.LogLevel;

public interface ErrorType {
	ErrorCode getCode();

	String getMessage();

	LogLevel getLogLevel();

	String getSubErrorCode();
}
