package com.prgrms.be.intermark.domain.newerd.booking.dto;

import javax.validation.constraints.NotNull;

import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingStatus;
import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;

import lombok.Getter;

public record ReserveConcertRequest(
	@Getter @NotNull Long userId,
	@Getter @NotNull Long seatId
) {

	public BookingHistory toBookingHistory(SeatInfoTobe seatInfoTobe) {
		return BookingHistory.builder()
			.userId(userId)
			.seatId(seatId)
			.concertId(seatInfoTobe.getConcertId())
			.concertScheduleId(seatInfoTobe.getConcertScheduleId())
			.stadiumId(seatInfoTobe.getStadiumId())
			.seatGradeId(seatInfoTobe.getSeatGradeId())
			.status(BookingStatus.COMPLETED)
			.build();
	}
}
