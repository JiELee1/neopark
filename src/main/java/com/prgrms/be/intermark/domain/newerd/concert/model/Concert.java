package com.prgrms.be.intermark.domain.newerd.concert.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.prgrms.be.intermark.common.dto.ImageResponseDTO;
import com.prgrms.be.intermark.common.entity.BaseEntity;
import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertCreateRequestDTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Concert")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Concert extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "stadium_id", nullable = false)
	private Long stadiumId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@NotBlank
	@Column(name = "title", nullable = false)
	private String title;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "view_rating", nullable = false, length = 10)
	private ViewRating viewRating;

	@NotBlank
	@Column(name = "thumbnail_path", nullable = false)
	private String thumbnailPath;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "genre", nullable = false, length = 20)
	private Genre genre;

	@NotNull
	@Lob
	@Column(name = "description", nullable = false)
	private String description;

	@NotNull
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@NotNull
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@NotNull
	@Positive
	@Column(name = "running_time", nullable = false)
	private int runningTime;

	// 보류
	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	@Builder
	private Concert(String title, String thumbnailPath,
		ViewRating viewRating,
		Genre genre, String description,
		LocalDate startDate, LocalDate endDate, int runningTime, Long stadiumId, Long userId) {
		this.title = title;
		this.thumbnailPath = thumbnailPath;
		this.viewRating = viewRating;
		this.genre = genre;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.runningTime = runningTime;
		this.isDeleted = false;
		this.stadiumId = stadiumId;
		this.userId = userId;
	}

	public static Concert of(ConcertCreateRequestDTO createRequestDto) {
		return Concert.builder()
			.title(createRequestDto.title())
			.viewRating(createRequestDto.viewRating())
			.genre(createRequestDto.genre())
			.description(createRequestDto.description())
			.startDate(createRequestDto.startDate())
			.endDate(createRequestDto.endDate())
			.runningTime(createRequestDto.runningTime())
			.build();
	}

	public static Concert fromDto(ConcertCreateRequestDTO createRequestDto, ImageResponseDTO thumbnail) {
		return Concert.builder()
			.title(createRequestDto.title())
			.thumbnailPath(thumbnail.path())
			.viewRating(createRequestDto.viewRating())
			.genre(createRequestDto.genre())
			.description(createRequestDto.description())
			.startDate(createRequestDto.startDate())
			.endDate(createRequestDto.endDate())
			.runningTime(createRequestDto.runningTime())
			.build();
	}

	public void deleteMusical() {
		this.isDeleted = true;
	}
}
