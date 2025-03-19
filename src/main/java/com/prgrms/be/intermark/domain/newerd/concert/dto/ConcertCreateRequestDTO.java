package com.prgrms.be.intermark.domain.newerd.concert.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.prgrms.be.intermark.domain.musical.dto.MusicalSeatCreateRequestDTO;
import com.prgrms.be.intermark.domain.musical.dto.MusicalSeatGradeCreateRequestDTO;
import com.prgrms.be.intermark.domain.newerd.concert.model.Genre;
import com.prgrms.be.intermark.domain.newerd.concert.model.ViewRating;

import lombok.Builder;

@Builder
public record ConcertCreateRequestDTO(
	@NotBlank String title,
	@NotNull ViewRating viewRating,
	@NotNull Genre genre,
	@NotBlank String description,
	@NotNull LocalDate startDate,
	@NotNull LocalDate endDate,
	@NotNull @Positive int runningTime,
	@NotNull long managerId,
	@NotNull long stadiumId,
	List<Long> actorIds, // 얘로 공연출연배우
	List<MusicalSeatGradeCreateRequestDTO> seatGrades, // 아래 둘로 좌석정보 만들면 됨
	List<MusicalSeatCreateRequestDTO> seats
) {
}
