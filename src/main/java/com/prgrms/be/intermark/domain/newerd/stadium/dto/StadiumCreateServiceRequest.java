package com.prgrms.be.intermark.domain.newerd.stadium.dto;

import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StadiumCreateServiceRequest {

	// 서비스 레이어에서 사용할 dto는 검증 필요 없음.

	private String name;

	private String address;

	private String imageUrl;

	@Builder
	private StadiumCreateServiceRequest(String name, String address, String imageUrl) {
		this.name = name;
		this.address = address;
		this.imageUrl = imageUrl;
	}

	public StadiumTobe toEntity() {
		return StadiumTobe.builder()
			.name(name)
			.address(address)
			.imageUrl(imageUrl)
			.build();
	}
}
