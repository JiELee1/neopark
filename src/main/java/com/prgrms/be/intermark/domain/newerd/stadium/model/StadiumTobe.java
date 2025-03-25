package com.prgrms.be.intermark.domain.newerd.stadium.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.be.intermark.common.entity.BaseEntity;
import com.prgrms.be.intermark.domain.newerd.stadium.dto.StadiumCreateRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "stadium_tobe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class StadiumTobe extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "address", nullable = false, unique = true)
	private String address;

	@Column(name = "image_url", nullable = false, length = 2000)
	private String imageUrl;

	@Builder
	private StadiumTobe(String name, String address, String imageUrl) {
		this.name = name;
		this.address = address;
		this.imageUrl = imageUrl;
	}

	public static StadiumTobe create(StadiumCreateRequest stadiumCreateRequest) {
		return StadiumTobe.builder()
			.name(stadiumCreateRequest.getName())
			.address(stadiumCreateRequest.getAddress())
			.imageUrl(stadiumCreateRequest.getImageUrl())
			.build();
	}
}
