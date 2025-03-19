package com.prgrms.be.intermark.domain.musical.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_detail_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MusicalDetailImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "original_file_name", nullable = false)
	private String originalFileName;

	@NotBlank
	@Column(name = "image_url", nullable = false, length = 2000)
	private String imageUrl;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	private Long concertId;

	@Builder
	public MusicalDetailImage(Long concertId, String originalFileName, String imageUrl) {
		this.concertId = concertId;
		this.originalFileName = originalFileName;
		this.imageUrl = imageUrl;
		this.isDeleted = false;
	}

	public void deleteMusicalDetailImage() {
		this.isDeleted = true;
	}
}
