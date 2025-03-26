package com.prgrms.be.intermark.domain.newerd.concertschedule.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertResponse;
import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertTobe;
import com.prgrms.be.intermark.domain.newerd.concert.model.Genre;
import com.prgrms.be.intermark.domain.newerd.concert.model.ViewRating;
import com.prgrms.be.intermark.domain.newerd.concert.repository.ConcertRepository;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleCreateRequest;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleCreateServiceRequest;
import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;
import com.prgrms.be.intermark.domain.newerd.stadium.repository.StadiumRepositoryTobe;

@SpringBootTest
@ActiveProfiles("h2")
class ConcertScheduleServiceTest {

	@Autowired
	private ConcertScheduleService concertScheduleService;

	@Autowired
	private ConcertRepository concertRepository;

	@Autowired
	private StadiumRepositoryTobe stadiumRepository;

	@Autowired
	private ConcertScheduleValidationService concertScheduleValidationService;

	@AfterEach
	void tearDown() {
		concertRepository.deleteAllInBatch();
		stadiumRepository.deleteAllInBatch();
	}

	@DisplayName("공연 기간 내에서만 공연 일정을 잡을 수 있다.")
	@Test
	void isScheduleInConcertPeriod() {
		// given
		LocalDate concertStartDate = LocalDate.of(2020, 1, 1);
		LocalDate concertEndDate = LocalDate.of(2020, 5, 1);
		int runningTime = 80;

		ConcertTobe concert = createConcert(concertStartDate, concertEndDate, runningTime);
		StadiumTobe stadium = createStadium();

		StadiumTobe savedStadium = stadiumRepository.save(stadium);
		ConcertTobe savedConcert = concertRepository.save(concert);
		ConcertResponse concertResponse = ConcertResponse.of(savedConcert);

		String scheduleStartTime = "2020-03-01 22:40";

		ConcertScheduleCreateRequest request = ConcertScheduleCreateRequest.builder()
			.concertId(savedConcert.getId())
			.stadiumId(savedStadium.getId())
			.startTime(scheduleStartTime)
			.build();

		ConcertScheduleCreateServiceRequest serviceRequest = request.toServiceRequest(concertResponse);

		// when
		boolean scheduleIsInConcertPeriod = concertScheduleValidationService.isScheduleInConcertPeriod(concertResponse,
			serviceRequest);

		// then
		Assertions.assertThat(scheduleIsInConcertPeriod).isTrue();

	}

	@DisplayName("공연 종료 시간이 공연 종료일을 초과하는 경우 공연 등록이 불가능하다.")
	@Test
	void isScheduleInConcertPeriod2() {
		// given
		LocalDate concertStartDate = LocalDate.of(2020, 1, 1);
		LocalDate concertEndDate = LocalDate.of(2020, 5, 1);
		int runningTime = 80;

		ConcertTobe concert = createConcert(concertStartDate, concertEndDate, runningTime);
		StadiumTobe stadium = createStadium();

		StadiumTobe savedStadium = stadiumRepository.save(stadium);
		ConcertTobe savedConcert = concertRepository.save(concert);
		ConcertResponse concertResponse = ConcertResponse.of(savedConcert);

		String scheduleStartTime = "2020-05-01 23:00";

		ConcertScheduleCreateRequest request = ConcertScheduleCreateRequest.builder()
			.concertId(savedConcert.getId())
			.stadiumId(savedStadium.getId())
			.startTime(scheduleStartTime)
			.build();

		ConcertScheduleCreateServiceRequest serviceRequest = request.toServiceRequest(concertResponse);

		// when
		boolean scheduleIsInConcertPeriod = concertScheduleValidationService.isScheduleInConcertPeriod(concertResponse,
			serviceRequest);

		// then
		Assertions.assertThat(scheduleIsInConcertPeriod).isFalse();
	}

	@DisplayName("공연 시작 시간이 공연 시작일 이전인 경우 공연 등록이 불가능하다.")
	@Test
	void isScheduleInConcertPeriod3() {

		// given
		LocalDate concertStartDate = LocalDate.of(2020, 1, 1);
		LocalDate concertEndDate = LocalDate.of(2020, 5, 1);
		int runningTime = 80;

		ConcertTobe concert = createConcert(concertStartDate, concertEndDate, runningTime);
		StadiumTobe stadium = createStadium();

		StadiumTobe savedStadium = stadiumRepository.save(stadium);
		ConcertTobe savedConcert = concertRepository.save(concert);
		ConcertResponse concertResponse = ConcertResponse.of(savedConcert);

		String scheduleStartTime = "2019-12-31 23:59";

		ConcertScheduleCreateRequest request = ConcertScheduleCreateRequest.builder()
			.concertId(savedConcert.getId())
			.stadiumId(savedStadium.getId())
			.startTime(scheduleStartTime)
			.build();

		ConcertScheduleCreateServiceRequest serviceRequest = request.toServiceRequest(concertResponse);

		// when
		boolean scheduleIsInConcertPeriod = concertScheduleValidationService.isScheduleInConcertPeriod(concertResponse,
			serviceRequest);

		// then
		Assertions.assertThat(scheduleIsInConcertPeriod).isFalse();
	}

	private static ConcertTobe createConcert(LocalDate startDate, LocalDate endDate, int runningTime) {
		return ConcertTobe.builder()
			.userId(1L)
			.title("레미제라블")
			.viewRating(ViewRating.ADULT)
			.thumbnailPath("/tmp")
			.genre(Genre.DRAMA)
			.description("설명입니다.")
			.startDate(startDate)
			.endDate(endDate)
			.runningTime(runningTime)
			.build();
	}

	private static StadiumTobe createStadium() {
		return StadiumTobe.builder()
			.name("예술의 전당")
			.address("서울특별시")
			.imageUrl("/tmp")
			.build();
	}

}
