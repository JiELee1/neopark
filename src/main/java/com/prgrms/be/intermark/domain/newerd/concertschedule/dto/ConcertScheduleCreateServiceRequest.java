package com.prgrms.be.intermark.domain.newerd.concertschedule.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertSchedule;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ConcertScheduleCreateServiceRequest {
	private long concertId;
	private long stadiumId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@Builder
	private ConcertScheduleCreateServiceRequest(long concertId, long stadiumId, LocalDateTime startTime,
		LocalDateTime endTime) {
		this.concertId = concertId;
		this.stadiumId = stadiumId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalDateTime toLocalDateTime(String time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(time, formatter);
	}

	public ConcertSchedule toEntity() {
		return ConcertSchedule.builder()
			.concertId(concertId)
			.stadiumId(stadiumId)
			.startTime(startTime)
			.endTime(endTime)
			.build();
	}
}
