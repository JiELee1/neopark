package com.prgrms.be.intermark.domain.newerd.seatInfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;

public interface SeatInfoRepositoryTobe extends JpaRepository<SeatInfoTobe, Long> {

	//@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	Optional<SeatInfoTobe> findByIdAndReserved(Long seatId, boolean isReserved);
}
