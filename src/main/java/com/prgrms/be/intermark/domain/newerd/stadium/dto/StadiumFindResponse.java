package com.prgrms.be.intermark.domain.newerd.stadium.dto;

import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;

import lombok.Builder;

@Builder
public record StadiumFindResponse(String name, String address, String imageUrl) {

	public static StadiumFindResponse from(StadiumTobe stadium) {
		return StadiumFindResponse.builder()
			.name(stadium.getName())
			.address(stadium.getAddress())
			.imageUrl(stadium.getImageUrl())
			.build();
	}
}
