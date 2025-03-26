package com.prgrms.be.intermark.domain.newerd.concertschedule.dto;

import java.time.LocalDateTime;

import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertScheduleTobe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertScheduleResponse {

	private Long id;
	private Long concertId;
	private Long stadiumId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@Builder
	private ConcertScheduleResponse(Long id, Long concertId, Long stadiumId, LocalDateTime startTime,
		LocalDateTime endTime) {
		this.id = id;
		this.concertId = concertId;
		this.stadiumId = stadiumId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public static ConcertScheduleResponse of(ConcertScheduleTobe concertSchedule) {
		return builder()
			.id(concertSchedule.getId())
			.concertId(concertSchedule.getConcertId())
			.stadiumId(concertSchedule.getStadiumId())
			.startTime(concertSchedule.getStartTime())
			.endTime(concertSchedule.getEndTime())
			.build();
	}
}
