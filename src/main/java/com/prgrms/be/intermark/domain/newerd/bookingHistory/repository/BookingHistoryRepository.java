package com.prgrms.be.intermark.domain.newerd.bookingHistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.be.intermark.domain.newerd.bookingHistory.model.BookingHistory;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
}
