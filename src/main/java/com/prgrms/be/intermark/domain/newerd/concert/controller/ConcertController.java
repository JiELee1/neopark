package com.prgrms.be.intermark.domain.newerd.concert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/concerts")
public class ConcertController {

	/*
		@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
		public ResponseEntity<Void> createMusical(
			@RequestPart @Valid MusicalCreateRequestDTO createRequestDto,
			@RequestPart(required = false) MultipartFile thumbnail,
			@RequestPart(required = false) List<MultipartFile> detailImages
		) {
		Long musicalId = musicalFacadeService.create(createRequestDto, thumbnail, detailImages);
		URI location = URI.create("/api/v1/musicals/" + musicalId);
		return ResponseEntity.created(location).build();

	}*/
}
