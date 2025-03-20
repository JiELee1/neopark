package com.prgrms.be.intermark.domain.newerd.bookingHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookingStatus {
	COMPLETED("예매 완료"),
	CANCELLED("예매 취소");

	private final String bookingStatus;

}
