package com.prgrms.be.intermark.domain.newerd.booking.dto;

import javax.validation.constraints.NotNull;

import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingStatus;
import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfo;

public record ReserveConcertRequest(
	@NotNull Long userId,
	@NotNull Long seatId
) {

	public BookingHistory toBookingHistory(SeatInfo seatInfo) {
		return BookingHistory.builder()
			.userId(userId)
			.seatId(seatId)
			.concertId(seatInfo.getConcertId())
			.concertScheduleId(seatInfo.getConcertScheduleId())
			.stadiumId(seatInfo.getStadiumId())
			.seatGradeId(seatInfo.getSeatGradeId())
			.status(BookingStatus.COMPLETED)
			.build();
	}
}
