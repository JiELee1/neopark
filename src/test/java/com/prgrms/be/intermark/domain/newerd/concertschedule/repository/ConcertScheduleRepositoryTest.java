package com.prgrms.be.intermark.domain.newerd.concertschedule.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertTobe;
import com.prgrms.be.intermark.domain.newerd.concert.model.Genre;
import com.prgrms.be.intermark.domain.newerd.concert.model.ViewRating;
import com.prgrms.be.intermark.domain.newerd.concert.repository.ConcertRepository;
import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertScheduleTobe;
import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;
import com.prgrms.be.intermark.domain.newerd.stadium.repository.StadiumRepositoryTobe;

@SpringBootTest
@ActiveProfiles("h2")
class ConcertScheduleRepositoryTest {

	@Autowired
	private ConcertScheduleRepository concertScheduleRepository;

	@Autowired
	private ConcertRepository concertRepository;

	@Autowired
	private StadiumRepositoryTobe stadiumRepository;

	@AfterEach
	void tearDown() {
		concertScheduleRepository.deleteAllInBatch();
	}

	@DisplayName("하나의 공연장에서 이미 존재하는 공연일정과 시간이 겹친다면 해당 공연일정은 등록이 불가능하다.")
	@Test
	void isConflictScheduleExist() {
		// given
		LocalDateTime scheduleStartTime = LocalDateTime.of(2020, 3, 1, 10, 30);
		LocalDateTime scheduleEndTime = LocalDateTime.of(2020, 3, 1, 12, 30);

		LocalDate concertStartDate = LocalDate.of(2020, 1, 1);
		LocalDate concertEndDate = LocalDate.of(2020, 4, 1);

		ConcertTobe concert = createConcert(concertStartDate, concertEndDate, 80);
		StadiumTobe stadium = createStadium();

		ConcertTobe savedConcert = concertRepository.save(concert);
		StadiumTobe savedStadium = stadiumRepository.save(stadium);

		ConcertScheduleTobe concertSchedule = ConcertScheduleTobe.builder()
			.startTime(scheduleStartTime)
			.endTime(scheduleEndTime)
			.concertId(savedConcert.getId())
			.stadiumId(savedStadium.getId())
			.build();

		concertScheduleRepository.save(concertSchedule);

		LocalDateTime testStartTime = LocalDateTime.of(2020, 3, 1, 9, 25);
		LocalDateTime testEndTime = LocalDateTime.of(2020, 3, 1, 14, 20);

		// when
		boolean conflictScheduleExist =
			concertScheduleRepository.isConflictScheduleExist(testStartTime, testEndTime, savedStadium.getId());

		// then
		Assertions.assertThat(conflictScheduleExist).isTrue();
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
