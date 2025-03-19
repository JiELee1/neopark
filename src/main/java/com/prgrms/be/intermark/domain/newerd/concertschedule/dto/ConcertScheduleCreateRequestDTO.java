package com.prgrms.be.intermark.domain.newerd.concertschedule.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.prgrms.be.intermark.domain.newerd.concert.model.Concert;
import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertSchedule;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record ConcertScheduleCreateRequestDTO(
	@NotNull long concertId,
	@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") String startTime
) {

	public LocalDateTime toLocalDateTime(String time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(time, formatter);
	}

	public LocalDateTime getStartTime() {
		return toLocalDateTime(this.startTime);
	}

	public LocalDateTime getEndTime(Concert concert) {
		return getStartTime().plusMinutes(concert.getRunningTime());
	}

	// TODO : 이게 맞나 싶음.. 엔티티를 전달하는게 맞나? 전달 파라미터 고려 필요. 이 클래스의 역할인지도 확인.
	public ConcertSchedule toEntity(Concert concert) {
		return ConcertSchedule.builder()
			.startTime(getStartTime())
			.endTime(getEndTime(concert))
			.concertId(concert.getId())
			.stadiumId(concert.getStadiumId())
			.build();
	}
}
