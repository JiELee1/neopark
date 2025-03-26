package com.prgrms.be.intermark.domain.newerd.seatInfo.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;

public interface SeatInfoRepositoryTobe extends JpaRepository<SeatInfoTobe, Long> {

	@Lock(value = LockModeType.OPTIMISTIC)
	Optional<SeatInfoTobe> findByIdAndReserved(Long seatId, boolean isReserved);

	Page<SeatInfoTobe> findAllByConcertScheduleId(Long concertScheduleId, Pageable pageable);
}
