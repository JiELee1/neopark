package com.prgrms.be.intermark.domain.newerd.seatInfo.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;
import com.prgrms.be.intermark.domain.newerd.seatInfo.repository.SeatInfoRepositoryTobe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatInfoValidationService {

	private final SeatInfoRepositoryTobe seatInfoRepositoryTobe;

	public SeatInfoTobe findAvailableSeatInfo(Long seatInfoId) {
		return seatInfoRepositoryTobe.findByIdAndReserved(seatInfoId, false)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않거나 이미 예약된 좌석입니다"));
	}

	public SeatInfoTobe findReservedSeatInfo(Long seatInfoId) {
		return seatInfoRepositoryTobe.findByIdAndReserved(seatInfoId, true)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않거나 이미 취소된 좌석입니다"));
	}
}
