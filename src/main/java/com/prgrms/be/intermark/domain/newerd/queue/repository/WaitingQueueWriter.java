package com.prgrms.be.intermark.domain.newerd.queue.repository;

import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
public interface WaitingQueueWriter {
    WaitingQueue createWaitingQueue(WaitingQueue waitingQueue);
    void moveToActiveQueue(String token);
    void removeActiveQueue(String token);
}