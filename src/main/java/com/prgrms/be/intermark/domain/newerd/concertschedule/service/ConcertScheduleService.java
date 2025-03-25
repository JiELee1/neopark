package com.prgrms.be.intermark.domain.newerd.concertschedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.common.dto.page.PageListIndexSize;
import com.prgrms.be.intermark.common.dto.page.PageResponseDTO;
import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertResponse;
import com.prgrms.be.intermark.domain.newerd.concert.model.Concert;
import com.prgrms.be.intermark.domain.newerd.concert.service.ConcertValidationService;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleCreateServiceRequest;
import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertSchedule;
import com.prgrms.be.intermark.domain.newerd.concertschedule.repository.ConcertScheduleRepository;
import com.prgrms.be.intermark.domain.newerd.stadium.service.StadiumValidationService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertScheduleService {

	private final ConcertScheduleRepository concertScheduleRepository;
	private final StadiumValidationService stadiumValidationService;
	private final ConcertScheduleValidationService concertScheduleValidationService;
	private final ConcertValidationService concertValidationService;

	@Transactional
	public Long create(ConcertScheduleCreateServiceRequest request) {

		/**
		 *  기존에는 공연 기간을 고려하지 않고, 겹치는 공연장과 공연시간만 고려함.
		 */

		// 1.공연장 존재여부 확인 및 공연 조회
		checkStadiumIsExist(request);
		ConcertResponse concert = findConcertById(request);

		// 2. 조회한 공연의 공연 기간 내에서 공연 일정을 잡을 수 있는지 확인.
		checkScheduleIsInConcertPeriod(request, concert);

		// 3. 해당 공연장에서 겹치는 시간대의 다른 공연이 있는지 확인
		checkConcertScheduleIsAvailable(request);

		// 4. 공연 일정 저장
		ConcertSchedule concertSchedule = request.toEntity();
		ConcertSchedule savedConcertSchedule = concertScheduleRepository.save(concertSchedule);

		return savedConcertSchedule.getId();
	}

	private void checkStadiumIsExist(ConcertScheduleCreateServiceRequest request) {
		stadiumValidationService.checkIsExist(request.getStadiumId());
	}

	private ConcertResponse findConcertById(ConcertScheduleCreateServiceRequest request) {
		Concert concert = concertValidationService.findActiveConcertById(request.getConcertId());
		return ConcertResponse.of(concert);
	}

	private void checkScheduleIsInConcertPeriod(ConcertScheduleCreateServiceRequest request, ConcertResponse concert) {
		concertScheduleValidationService.checkScheduleIsInConcertPeriod(request, concert);
	}

	private void checkConcertScheduleIsAvailable(ConcertScheduleCreateServiceRequest request) {
		concertScheduleValidationService.checkConcertScheduleCreateRequestIsPossible(request);
	}

	public ConcertScheduleResponse findConcertScheduleById(Long concertScheduleId) {
		ConcertSchedule concertSchedule = concertScheduleValidationService.findById(concertScheduleId);
		return ConcertScheduleResponse.of(concertSchedule);
	}

	public PageResponseDTO<ConcertSchedule, ConcertScheduleResponse> findAllSchedules(Pageable pageable) {
		Page<ConcertSchedule> schedulePages = concertScheduleRepository.findAll(pageable);
		return new PageResponseDTO<>(schedulePages, ConcertScheduleResponse::of,
			PageListIndexSize.SCHEDULE_LIST_INDEX_SIZE);
	}
}
