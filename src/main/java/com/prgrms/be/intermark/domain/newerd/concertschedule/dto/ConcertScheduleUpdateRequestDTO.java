package com.prgrms.be.intermark.domain.newerd.concertschedule.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.prgrms.be.intermark.domain.newerd.concert.model.Concert;

import lombok.Builder;

@Builder
public record ConcertScheduleUpdateRequestDTO(@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") String startTime) {

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

}
