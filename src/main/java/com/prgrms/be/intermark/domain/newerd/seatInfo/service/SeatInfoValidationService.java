package com.prgrms.be.intermark.domain.newerd.seatInfo.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfo;
import com.prgrms.be.intermark.domain.newerd.seatInfo.repository.SeatInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatInfoValidationService {

	private final SeatInfoRepository seatInfoRepository;

	public SeatInfo findAvailableSeatInfo(Long seatInfoId) {
		return seatInfoRepository.findByIdAndReserved(seatInfoId, false)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않거나 이미 예약된 좌석입니다"));
	}

	public SeatInfo findReservedSeatInfo(Long seatInfoId) {
		return seatInfoRepository.findByIdAndReserved(seatInfoId, true)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않거나 이미 취소된 좌석입니다"));
	}
}
