package com.prgrms.be.intermark.domain.newerd.stadium.dto;

import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StadiumResponse {

	private Long id;
	private String name;
	private String address;
	private String imageUrl;

	@Builder
	private StadiumResponse(Long id, String name, String address, String imageUrl) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.imageUrl = imageUrl;
	}

	public static StadiumResponse of(StadiumTobe stadium) {
		return builder()
			.id(stadium.getId())
			.name(stadium.getName())
			.address(stadium.getAddress())
			.imageUrl(stadium.getImageUrl())
			.build();
	}
}
