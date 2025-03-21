package com.prgrms.be.intermark.domain.newerd.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingStatus;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {

	Optional<BookingHistory> findByIdAndUserIdAndStatus(Long bookingId, Long userId, BookingStatus status);
}
