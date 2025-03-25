package com.prgrms.be.intermark.domain.newerd.concert.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.prgrms.be.intermark.domain.newerd.concert.model.Genre;
import com.prgrms.be.intermark.domain.newerd.concert.model.ViewRating;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertCreateRequest {

	@NotBlank
	String title;

	@NotNull
	ViewRating viewRating;

	@NotNull
	Genre genre;

	@NotNull
	String description;

	@NotNull
	LocalDate startDate;

	@NotNull
	LocalDate endDate;

	@NotNull
	int runningTime;

	@Positive
	long managerId;

	@Size(min = 1, message = "리스트에는 최소 1개 이상의 값이 있어야 합니다.")
	List<ConcertActorRegister> concertActorRegisterDTOS;

	@Builder
	private ConcertCreateRequest(String title, ViewRating viewRating, Genre genre, String description,
		LocalDate startDate, LocalDate endDate, int runningTime, long managerId,
		List<ConcertActorRegister> concertActorRegisterDTOS) {
		this.title = title;
		this.viewRating = viewRating;
		this.genre = genre;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.runningTime = runningTime;
		this.managerId = managerId;
		this.concertActorRegisterDTOS = concertActorRegisterDTOS;
	}

	public ConcertCreateServiceRequest toServiceRequest() {
		return ConcertCreateServiceRequest.builder()
			.title(title)
			.viewRating(viewRating)
			.genre(genre)
			.description(description)
			.endDate(endDate)
			.runningTime(runningTime)
			.managerId(managerId)
			.startDate(startDate)
			.concertActorRegisterDTOS(concertActorRegisterDTOS)
			.build();
	}
}


