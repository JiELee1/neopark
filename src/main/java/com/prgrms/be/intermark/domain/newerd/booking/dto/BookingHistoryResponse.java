package com.prgrms.be.intermark.domain.newerd.booking.dto;

import com.prgrms.be.intermark.domain.newerd.booking.model.BookingStatus;

import lombok.Builder;

@Builder
public record BookingHistoryResponse(
	Long bookingId,
	String nickname,
	BookingStatus bookingStatus
	// @NotNull TicketResponseMusicalDTO musical,
	// @NotNull TicketResponseSeatDTO seat,
	// @NotNull TicketResponseStadiumDTO stadium,
) {
}
