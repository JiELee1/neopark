package com.prgrms.be.intermark.domain.newerd.actor.dto;

import java.time.LocalDate;

import com.prgrms.be.intermark.domain.newerd.actor.model.ActorTobe;
import com.prgrms.be.intermark.domain.newerd.actor.model.Gender;

import lombok.Builder;

@Builder
public record ActorResponse(Long id, String name, String profileImageUrl, LocalDate birth, Gender gender) {

	public static ActorResponse from(ActorTobe actor) {
		return ActorResponse.builder()
			.id(actor.getId())
			.name(actor.getName())
			.profileImageUrl(actor.getProfileImageUrl())
			.birth(actor.getBirth())
			.gender(actor.getGender())
			.build();
	}
}
