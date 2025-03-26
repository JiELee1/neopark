package com.prgrms.be.intermark.domain.newerd.booking.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prgrms.be.intermark.domain.newerd.booking.dto.BookingHistoryDTO;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingStatus;
import com.prgrms.be.intermark.domain.newerd.booking.repository.BookingHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingValidationService {

	private final BookingHistoryRepository bookingHistoryRepository;

	public BookingHistory findBookingHistory(Long bookingId, Long userId) {
		return bookingHistoryRepository.findByIdAndUserIdAndStatus(bookingId, userId, BookingStatus.COMPLETED)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않거나 이미 취소된 예매내역입니다"));
	}

	public BookingHistory findById(Long bookingId) {
		return bookingHistoryRepository.findById(bookingId)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 예매내역입니다"));
	}

	public Page<BookingHistoryDTO> findPageBookingHistoryDTO(Long userId, Long concertId, Pageable pageable) {
		return bookingHistoryRepository.findBookingHistoryByCondition(userId, concertId, pageable);
	}

	public BookingHistoryDTO findBookingHistoryDTO(Long bookingId) {
		return bookingHistoryRepository.findDTOById(bookingId)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 예매내역입니다"));
	}

}
