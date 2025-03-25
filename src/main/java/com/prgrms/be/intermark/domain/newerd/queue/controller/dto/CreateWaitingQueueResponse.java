package com.prgrms.be.intermark.domain.newerd.queue.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;

public record CreateWaitingQueueResponse(
	Long id,
	String token,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime joinedAt
) {

	public static CreateWaitingQueueResponse of(final WaitingQueue waitingQueue) {
		return new CreateWaitingQueueResponse(
			waitingQueue.getId(),
			waitingQueue.getToken(),
			waitingQueue.getActivatedAt()
		);
	}
}
