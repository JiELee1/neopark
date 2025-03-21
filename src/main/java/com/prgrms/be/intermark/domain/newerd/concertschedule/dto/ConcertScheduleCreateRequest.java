package com.prgrms.be.intermark.domain.newerd.concertschedule.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertResponse;
import com.prgrms.be.intermark.domain.newerd.concert.model.Concert;
import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertSchedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertScheduleCreateRequest {

	@NotNull
	@Positive
	private Long concertId;

	@NotNull
	@Positive
	private Long stadiumId;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private String startTime;

	private String endTime;

	@Builder
	private ConcertScheduleCreateRequest(Long concertId, Long stadiumId, String startTime, String endTime) {
		this.concertId = concertId;
		this.stadiumId = stadiumId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ConcertScheduleCreateServiceRequest toServiceRequest(ConcertResponse concert) {
		return ConcertScheduleCreateServiceRequest.builder()
			.concertId(concertId)
			.stadiumId(stadiumId)
			.startTime(getStartTime())
			.endTime(getEndTime(concert.getRunningTime())).build();
	}

	public LocalDateTime getStartTime() {
		return toLocalDateTime(this.startTime);
	}

	public LocalDateTime toLocalDateTime(String time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(time, formatter);
	}

	public LocalDateTime getEndTime(Concert concert) {
		return getStartTime().plusMinutes(concert.getRunningTime());
	}

	public LocalDateTime getEndTime(int runningTime) {
		return getStartTime().plusMinutes(runningTime);
	}

	public ConcertSchedule toEntity(Concert concert) {
		return ConcertSchedule.builder()
			.startTime(getStartTime())
			.endTime(getEndTime(concert))
			.concertId(concert.getId())
			.build();
	}
}
