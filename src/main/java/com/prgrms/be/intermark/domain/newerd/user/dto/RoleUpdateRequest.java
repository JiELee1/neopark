package com.prgrms.be.intermark.domain.newerd.user.dto;

import javax.validation.constraints.NotNull;

import com.prgrms.be.intermark.domain.newerd.user.model.UserRole;

public record RoleUpdateRequest(
	@NotNull UserRole role
) {
}
