package com.prgrms.be.intermark.domain.newerd.queue.controller.dto;

import com.prgrms.be.intermark.domain.newerd.queue.model.dto.WaitingQueueInfo;
import com.prgrms.be.intermark.domain.newerd.queue.model.enums.QueueStatus;

public record GetWaitingQueueStatusResponse(
	Long id,
	Long userId,
	QueueStatus status,
	Long waitingCount
) {

	public static GetWaitingQueueStatusResponse of(final WaitingQueueInfo waitingQueueInfo) {
		return new GetWaitingQueueStatusResponse(
			waitingQueueInfo.id(),
			waitingQueueInfo.userId(),
			waitingQueueInfo.status(),
			waitingQueueInfo.waitingNumber()
		);
	}
}
