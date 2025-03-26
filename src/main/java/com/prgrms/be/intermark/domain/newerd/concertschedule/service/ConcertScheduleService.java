package com.prgrms.be.intermark.domain.newerd.concertschedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertResponse;
import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertTobe;
import com.prgrms.be.intermark.domain.newerd.concert.service.ConcertValidationService;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleCreateServiceRequest;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleResponse;
import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertScheduleTobe;
import com.prgrms.be.intermark.domain.newerd.concertschedule.repository.ConcertScheduleRepository;
import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;
import com.prgrms.be.intermark.domain.newerd.seatInfo.service.SeatInfoService;
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
	private final SeatInfoService seatInfoService;

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
		ConcertScheduleTobe concertSchedule = request.toEntity();
		ConcertScheduleTobe savedConcertSchedule = concertScheduleRepository.save(concertSchedule);

		return savedConcertSchedule.getId();
	}

	private void checkStadiumIsExist(ConcertScheduleCreateServiceRequest request) {
		stadiumValidationService.checkIsExist(request.getStadiumId());
	}

	private ConcertResponse findConcertById(ConcertScheduleCreateServiceRequest request) {
		ConcertTobe concert = concertValidationService.findActiveConcertById(request.getConcertId());
		return concert.createResponse();
	}

	private void checkScheduleIsInConcertPeriod(ConcertScheduleCreateServiceRequest request, ConcertResponse concert) {
		concertScheduleValidationService.checkScheduleIsInConcertPeriod(request, concert);
	}

	private void checkConcertScheduleIsAvailable(ConcertScheduleCreateServiceRequest request) {
		concertScheduleValidationService.checkConcertScheduleCreateRequestIsPossible(request);
	}

	public ConcertScheduleResponse findConcertScheduleById(Long concertScheduleId) {
		ConcertScheduleTobe concertSchedule = concertScheduleValidationService.findById(concertScheduleId);
		return concertSchedule.createResponse();
	}

	public Page<ConcertScheduleResponse> findAllSchedules(Pageable pageable) {
		Page<ConcertScheduleTobe> schedulePages = concertScheduleRepository.findAll(pageable);
		return schedulePages.map(ConcertScheduleTobe::createResponse);
	}

	public Page<ConcertScheduleResponse> findSchedulesByConcertId(Long concertId, Pageable pageable) {
		Page<ConcertScheduleTobe> concertSchedules = concertScheduleRepository.findByConcertId(concertId, pageable);
		return concertSchedules.map(ConcertScheduleTobe::createResponse);
	}

	public Page<SeatInfoTobe> findAllByConcertScheduleId(Long concertScheduleId, Pageable pageable) {
		return seatInfoService.findAllByConcertScheduleId(
			concertScheduleId, pageable);
	}
}
