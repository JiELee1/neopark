package com.prgrms.be.intermark.domain.newerd.booking.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingStatus;

import io.lettuce.core.dynamic.annotation.Param;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {

	Optional<BookingHistory> findByIdAndUserIdAndStatus(Long bookingId, Long userId, BookingStatus status);

	@Query(value = "SELECT b FROM BookingHistory b " +
		"WHERE CASE WHEN :userId is null THEN TRUE ELSE (b.userId = :userId) END AND " +
		"CASE WHEN :concertId is null THEN TRUE ELSE (b.concertId = :concertId) END",
		countQuery = "SELECT b FROM BookingHistory b " +
			"WHERE CASE WHEN :userId is null THEN TRUE ELSE (b.userId = :userId) END AND " +
			"CASE WHEN :concertId is null THEN TRUE ELSE (b.concertId = :concertId) END")
	Page<BookingHistory> findAllByUserIdAndConcertId(@Param("userId") Long userId,
		@Param("concertId") Long concertId, Pageable pageable);
}
