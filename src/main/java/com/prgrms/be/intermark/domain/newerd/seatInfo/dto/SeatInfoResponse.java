package com.prgrms.be.intermark.domain.newerd.seatInfo.dto;

import lombok.Builder;

@Builder
public record SeatInfoResponse(
	int row,
	int col
) {
}
