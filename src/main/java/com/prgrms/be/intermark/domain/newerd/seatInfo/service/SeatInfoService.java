package com.prgrms.be.intermark.domain.newerd.seatInfo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;
import com.prgrms.be.intermark.domain.newerd.seatInfo.repository.SeatInfoRepositoryTobe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatInfoService {
	private final SeatInfoRepositoryTobe seatInfoRepositoryTobe;

	public Page<SeatInfoTobe> findAllByConcertScheduleId(Long concertScheduleId, Pageable pageable) {
		return seatInfoRepositoryTobe.findAllByConcertScheduleId(concertScheduleId, pageable);
	}
}
