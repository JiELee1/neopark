package com.prgrms.be.intermark.domain.newerd.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.booking.dto.BookingHistoryResponse;
import com.prgrms.be.intermark.domain.newerd.booking.dto.ReserveConcertRequest;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.repository.BookingHistoryRepository;
import com.prgrms.be.intermark.domain.newerd.concertschedule.service.ConcertScheduleValidationService;
import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;
import com.prgrms.be.intermark.domain.newerd.seatInfo.service.SeatInfoValidationService;
import com.prgrms.be.intermark.domain.newerd.user.model.UserTobe;
import com.prgrms.be.intermark.domain.newerd.user.service.UserValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

	private final UserValidationService userValidationService;
	private final SeatInfoValidationService seatInfoValidationService;
	private final ConcertScheduleValidationService concertScheduleValidationService;
	private final BookingHistoryRepository bookingHistoryRepository;
	private final BookingValidationService bookingValidationService;

	//TODO 토큰에서 인증하는걸로 바꿔서 user검증로직은 지울예정
	@Transactional
	public Long reserveConcert(ReserveConcertRequest reserveConcertRequest) {
		userValidationService.findActiveUser(reserveConcertRequest.userId());
		SeatInfoTobe seatInfoTobe = seatInfoValidationService.findAvailableSeatInfo(reserveConcertRequest.seatId());
		concertScheduleValidationService.findAvailableConcertSchedule(seatInfoTobe.getConcertScheduleId());

		seatInfoTobe.reserve();
		return bookingHistoryRepository.save(reserveConcertRequest.toBookingHistory(seatInfoTobe)).getId();
	}

	@Transactional
	public void cancelConcert(Long userId, Long historyId) {
		BookingHistory bookingHistory = bookingValidationService.findBookingHistory(historyId, userId);
		SeatInfoTobe seatInfoTobe = seatInfoValidationService.findReservedSeatInfo(bookingHistory.getSeatId());

		seatInfoTobe.cancel();
		bookingHistory.cancel();
	}

/*	//TODO 코드 합치고 response 더 추가해야함
	public Page<BookingHistoryResponse> getBookingHistoryPage(BookingHistoryCondition bookingHistoryCondition,
		Pageable pageable) {
		Page<BookingHistory> bookingHistoryPage = bookingHistoryRepository.findAllByUserIdAndConcertId(
			bookingHistoryCondition.userId(), bookingHistoryCondition.concertId(), pageable);
		return bookingHistoryPage.map(b -> {
			UserTobe user = userValidationService.findActiveUser(b.getUserId());
			return b.toBookingHistoryResponse(user);
		});
	}*/

	public BookingHistoryResponse getBookingHistory(Long bookingId) {
		BookingHistory bookingHistory = bookingValidationService.findById(bookingId);
		UserTobe user = userValidationService.findActiveUser(bookingHistory.getUserId());
		return bookingHistory.toBookingHistoryResponse(user);
	}

}
