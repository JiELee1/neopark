package com.prgrms.be.intermark.domain.newerd.queue.repository;

import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;

import java.util.List;

public interface WaitingQueueReader {
    WaitingQueue getByToken(String token);
    List<WaitingQueue> getWaitingQueuesToBeActivated(int activationCount);
    WaitingQueue getActiveQueueByToken(String token);

}