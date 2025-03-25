package com.prgrms.be.intermark.common.dto;

import lombok.Builder;

@Builder
public record ImageSaveDTO(
	String originalFileName,
	String path) {
}
