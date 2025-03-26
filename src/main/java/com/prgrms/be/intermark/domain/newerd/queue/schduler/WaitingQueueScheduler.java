package com.prgrms.be.intermark.domain.newerd.queue.schduler;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
import com.prgrms.be.intermark.domain.newerd.queue.model.enums.QueueStatus;
import com.prgrms.be.intermark.domain.newerd.queue.service.WaitingQueueService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [ 대기열 활성화 스케줄러 구현 이유 ]
 * 1. 은행 창구 방식(순차 처리)
 * 장점: 한 번에 한 명씩 처리하므로 공유 자원에 대한 접근이 엄격하게 순차적으로 일어나, 동시성 문제나 경합 상황을 자연스럽게 회피할 수 있습니다.
 * 단점: 단일 처리 흐름은 병목 현상이 발생하기 쉽고, 수많은 요청이 몰릴 경우 처리 속도가 현저히 떨어집니다.
 *	--------------------------------
 * 2.놀이공원 방식(동시/병렬 처리)
 * 장점: 여러 요청을 동시에 처리할 수 있어 전체적인 처리량을 크게 늘릴 수 있습니다.
 * 		현대 티켓팅 시스템은 분산 큐, 캐시, 분산 락, 비동기 메시지 처리 등의 기법을 통해 동시성 문제를 해결하면서도 빠른 응답을 제공합니다.
 * 단점: 공유 자원에 대한 동시 접근을 제어하기 위해 추가적인 동기화 메커니즘이나 트랜잭션 관리가 필요합니다.
 *	--------------------------------
 * => 결론적으로, 대규모 트래픽에서는 단순히 순차 처리하는 은행 창구 방식은 처리량에서 한계가 있기 때문에,
 *    동시성을 적극 활용하는 놀이공원 방식(분산 시스템 기반 병렬 처리)이 더 적합함.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingQueueScheduler {
	private final WaitingQueueService waitingQueueService;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Scheduled(fixedDelayString = "5000")  // 5초마다 실행
	public void activateWaitingQueue() {
		log.info("대기열 활성화 스케줄러 실행");
		final List<WaitingQueue> waitingQueues = waitingQueueService.getWaitingQueuesToBeActivated(1000);

		// 대기열이 없으면 종료
		if (waitingQueues == null) {
			return;
		}

		AtomicInteger processedCount = new AtomicInteger();
		waitingQueues.forEach(waitingQueue -> {
			try {
				if (waitingQueue.getStatus() == QueueStatus.WAITING) {
					waitingQueueService.activateQueue(waitingQueue.getToken());
					processedCount.getAndIncrement();
					log.info("대기열 활성화 완료 (Token: {})", waitingQueue.getToken());

				}
			} catch (Exception e) {
				log.warn("대기열 활성화 중 오류 발생 (Token: {}): {}", waitingQueue.getToken(), e.getMessage());
			}
		});
		log.info("총 {}개의 대기열이 활성화 처리되었습니다.", processedCount);
	}
}
