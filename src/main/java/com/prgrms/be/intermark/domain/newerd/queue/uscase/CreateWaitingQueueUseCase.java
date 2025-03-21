package com.prgrms.be.intermark.domain.newerd.queue.uscase;

import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
import com.prgrms.be.intermark.domain.newerd.queue.service.WaitingQueueService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateWaitingQueueUseCase {

	private final WaitingQueueService waitingQueueService;

	public WaitingQueue createWaitingQueue(final Long userId, final String token) {
		WaitingQueue waitingQueue = waitingQueueService.createWaitingQueue(userId, token);
		log.debug("대기열 생성 완료: {}", waitingQueue.toString());
		return waitingQueue;
	}
}
