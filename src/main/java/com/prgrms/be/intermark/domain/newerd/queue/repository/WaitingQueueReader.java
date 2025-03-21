package com.prgrms.be.intermark.domain.newerd.queue.repository;

import java.util.List;

import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;

public interface WaitingQueueReader {
	WaitingQueue getByToken(String token);

	List<WaitingQueue> getWaitingQueuesToBeActivated(int activationCount);

	WaitingQueue getActiveQueueByToken(String token);

}
