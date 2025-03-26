package com.prgrms.be.intermark.domain.newerd.concert.dto;

import java.time.LocalDate;

import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertTobe;
import com.prgrms.be.intermark.domain.newerd.concert.model.Genre;
import com.prgrms.be.intermark.domain.newerd.concert.model.ViewRating;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConcertResponse {
	private Long id;
	private String title;
	private ViewRating viewRating;
	private Genre genre;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private long managerId;
	private int runningTime;

	@Builder
	private ConcertResponse(Long id, String title, ViewRating viewRating, Genre genre, String description,
		LocalDate startDate, LocalDate endDate, long managerId, int runningTime) {
		this.id = id;
		this.title = title;
		this.viewRating = viewRating;
		this.genre = genre;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.managerId = managerId;
		this.runningTime = runningTime;
	}

	public static ConcertResponse of(ConcertTobe concert) {
		return ConcertResponse.builder()
			.id(concert.getId())
			.title(concert.getTitle())
			.viewRating(concert.getViewRating())
			.genre(concert.getGenre())
			.description(concert.getDescription())
			.startDate(concert.getStartDate())
			.endDate(concert.getEndDate())
			.managerId(concert.getUserId())
			.runningTime(concert.getRunningTime())
			.build();
	}
}
