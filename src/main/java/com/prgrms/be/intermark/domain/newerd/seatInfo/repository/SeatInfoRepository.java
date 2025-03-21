package com.prgrms.be.intermark.domain.newerd.seatInfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfo;

public interface SeatInfoRepository extends JpaRepository<SeatInfo, Long> {

	Optional<SeatInfo> findByIdAndReserved(Long seatId, boolean isReserved);
}
