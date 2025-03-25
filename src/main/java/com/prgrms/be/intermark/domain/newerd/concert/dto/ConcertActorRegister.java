package com.prgrms.be.intermark.domain.newerd.concert.dto;

import javax.validation.constraints.NotBlank;

public record ConcertActorRegister(
	@NotBlank String name,
	@NotBlank Long actorId
) {
}
