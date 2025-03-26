package com.prgrms.be.intermark.domain.newerd.booking.dto;

import com.prgrms.be.intermark.domain.newerd.booking.model.BookingStatus;
import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertResponse;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleResponse;
import com.prgrms.be.intermark.domain.newerd.seatInfo.dto.SeatInfoResponse;
import com.prgrms.be.intermark.domain.newerd.stadium.dto.StadiumResponse;

import lombok.Builder;

@Builder
public record BookingHistoryResponse(
	Long bookingId,
	String nickname,
	BookingStatus bookingStatus,
	ConcertResponse concertResponse,
	ConcertScheduleResponse concertScheduleResponse,
	SeatInfoResponse seatInfoResponse,
	StadiumResponse stadiumResponse
) {
}
