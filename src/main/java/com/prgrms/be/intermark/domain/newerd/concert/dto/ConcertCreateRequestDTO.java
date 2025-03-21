package com.prgrms.be.intermark.domain.newerd.concert.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.prgrms.be.intermark.domain.newerd.concert.model.Genre;
import com.prgrms.be.intermark.domain.newerd.concert.model.ViewRating;

import lombok.Builder;

@Builder
public record ConcertCreateRequestDTO(
	@NotBlank String title,
	@NotBlank ViewRating viewRating,
	@NotBlank Genre genre,
	@NotNull String description,

	// TODO : 공연 예약은 현재부터 2달 이후부터 가능하다. 어노테이션 생성해서 검증해보기
	@NotBlank LocalDate startDate,
	@NotBlank LocalDate endDate,
	@Positive int runningTime,
	@Positive long managerId,
	@Positive long stadiumId,

	// TODO : 배우 이름과, 배우 고유 아이디를 전달해주면 우리 디비에서 검색한다. 동명이인이 있을 수도 있으니
	@Size(min = 1, message = "리스트에는 최소 1개 이상의 값이 있어야 합니다.")
	List<ConcertActorRegisterDTO> concertActorRegisterDTOS
) {
}
