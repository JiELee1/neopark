package com.prgrms.be.intermark.domain.newerd.stadium.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StadiumCreateRequest {

	@NotBlank
	private String name;

	@NotBlank
	private String address;

	@NotBlank
	private String imageUrl;

	@Builder
	private StadiumCreateRequest(String name, String address, String imageUrl) {
		this.name = name;
		this.address = address;
		this.imageUrl = imageUrl;
	}

	public StadiumCreateServiceRequest toServiceRequest() {
		return StadiumCreateServiceRequest.builder()
			.name(name)
			.address(address)
			.imageUrl(imageUrl)
			.build();

	}
}
