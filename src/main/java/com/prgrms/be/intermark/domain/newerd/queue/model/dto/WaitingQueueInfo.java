package com.prgrms.be.intermark.domain.newerd.queue.model.dto;

import java.time.LocalDateTime;

import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
import com.prgrms.be.intermark.domain.newerd.queue.model.enums.QueueStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
public record WaitingQueueInfo(
	Long id,
	Long userId,
	String token,
	@Getter QueueStatus status,
	LocalDateTime activatedAt,
	LocalDateTime expiredAt,
	LocalDateTime lastActionedAt,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	Long waitingNumber
) {

	public static WaitingQueueInfo of(final WaitingQueue waitingQueue, final Long waitingNumber) {
		return new WaitingQueueInfo(
			waitingQueue.getId(),
			waitingQueue.getUserId(),
			waitingQueue.getToken(),
			waitingQueue.getStatus(),
			waitingQueue.getActivatedAt(),
			waitingQueue.getExpiredAt(),
			waitingQueue.getLastActionedAt(),
			waitingQueue.getCreatedAt(),
			waitingQueue.getUpdatedAt(),
			waitingNumber
		);
	}

	public static WaitingQueueInfo of(final WaitingQueue currentWaitingQueue) {
		return WaitingQueueInfo.builder()
			.userId(currentWaitingQueue.getUserId())
			.token(currentWaitingQueue.getToken())
			.status(currentWaitingQueue.getStatus())
			.waitingNumber(currentWaitingQueue.getWaitingOrder())
			.build();
	}

	public WaitingQueue getWaitingQueue() {
		return WaitingQueue.builder()
			.id(id)
			.userId(userId)
			.token(token)
			.status(status)
			.activatedAt(activatedAt)
			.expiredAt(expiredAt)
			.lastActionedAt(lastActionedAt)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.waitingOrder(waitingNumber)
			.build();
	}
}
