package com.prgrms.be.intermark.domain.newerd.concert.dto;

import java.time.LocalDate;
import java.util.List;

import com.prgrms.be.intermark.domain.newerd.concert.model.Genre;
import com.prgrms.be.intermark.domain.newerd.concert.model.ViewRating;

import lombok.Builder;

@Builder
public record ConcertCreateServiceRequest(
	String title,
	ViewRating viewRating,
	Genre genre,
	String description,
	LocalDate startDate,
	LocalDate endDate,
	int runningTime,
	long managerId,
	List<ConcertActorRegister> concertActorRegisterDTOS
) {
}
