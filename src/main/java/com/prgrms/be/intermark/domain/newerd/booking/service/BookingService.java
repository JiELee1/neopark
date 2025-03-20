package com.prgrms.be.intermark.domain.newerd.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.booking.dto.ReserveConcertRequest;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.repository.BookingHistoryRepository;
import com.prgrms.be.intermark.domain.newerd.concertschedule.service.ConcertScheduleValidationService;
import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfo;
import com.prgrms.be.intermark.domain.newerd.seatInfo.service.SeatInfoValidationService;
import com.prgrms.be.intermark.domain.newerd.user.service.UserValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

	private final UserValidationService userValidationService;
	private final SeatInfoValidationService seatInfoValidationService;
	private final ConcertScheduleValidationService concertScheduleValidationService;
	private final BookingHistoryRepository bookingHistoryRepository;
	private final BookingValidationService bookingValidationService;

	@Transactional
	public Long reserveConcert(ReserveConcertRequest reserveConcertRequest) {
		userValidationService.findActiveUser(reserveConcertRequest.userId());
		SeatInfo seatInfo = seatInfoValidationService.findAvailableSeatInfo(reserveConcertRequest.seatId());
		concertScheduleValidationService.findAvailableConcertSchedule(seatInfo.getConcertScheduleId());

		seatInfo.reserve();
		return bookingHistoryRepository.save(reserveConcertRequest.toBookingHistory(seatInfo)).getId();
	}

	@Transactional
	public void cancelConcert(Long userId, Long historyId) {
		BookingHistory bookingHistory = bookingValidationService.findBookingHistory(historyId, userId);
		SeatInfo seatInfo = seatInfoValidationService.findReservedSeatInfo(bookingHistory.getSeatId());

		seatInfo.cancel();
		bookingHistory.cancel();
	}

}
