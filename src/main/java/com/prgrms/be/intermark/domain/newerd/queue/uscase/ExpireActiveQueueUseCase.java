package com.prgrms.be.intermark.domain.newerd.queue.uscase;

import com.prgrms.be.intermark.domain.newerd.queue.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class ExpireActiveQueueUseCase {

    private final WaitingQueueService waitingQueueService;

    public void expireActiveQueue(final String token) {
        waitingQueueService.expireActiveQueue(token);
        log.debug("대기열 종료");
    }
}