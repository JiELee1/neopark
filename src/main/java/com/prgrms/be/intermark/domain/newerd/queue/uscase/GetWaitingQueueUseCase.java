package com.prgrms.be.intermark.domain.newerd.queue.uscase;

import com.prgrms.be.intermark.domain.newerd.queue.model.dto.WaitingQueueInfo;
import com.prgrms.be.intermark.domain.newerd.queue.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@UseCase
@RequiredArgsConstructor
public class GetWaitingQueueUseCase {

    private final WaitingQueueService waitingQueueService;

    public WaitingQueueInfo getWaitingQueueInfo(final String token) {
        WaitingQueueInfo waitingQueueInfo = waitingQueueService.getWaitingQueueInfo(token);
        log.debug("대기열 정보 조회 완료: {}", waitingQueueInfo.toString());
        return waitingQueueInfo;
    }
}