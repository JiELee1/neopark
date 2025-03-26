package com.prgrms.be.intermark.domain.newerd.queue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.queue.exception.CoreException;
import com.prgrms.be.intermark.domain.newerd.queue.exception.WaitingQueueErrorType;
import com.prgrms.be.intermark.domain.newerd.queue.model.dto.WaitingQueueInfo;
import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
import com.prgrms.be.intermark.domain.newerd.queue.model.enums.QueueStatus;
import com.prgrms.be.intermark.domain.newerd.queue.repository.WaitingQueueReader;
import com.prgrms.be.intermark.domain.newerd.queue.repository.WaitingQueueWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitingQueueService {

	private final WaitingQueueReader waitingQueueReader;
	private final WaitingQueueWriter waitingQueueWriter;

	/**
	 * 대기열 생성 (사용자가 대기열에 진입할 때 호출함.)
	 * @param userId 사용자 ID
	 * @return 생성된 대기열 정보
	 */
	public WaitingQueue createWaitingQueue(final Long userId, final String token) {
		return waitingQueueWriter.createWaitingQueue(new WaitingQueue(userId, token));
	}

	/**
	 * 대기열 정보 조회 (사용자가 대기열 조회할 때 호출함.)
	 * 현재 대기열 정보를 조회하고 대기 순서를 계산하여 반환
	 * @param token 대기열 토큰 정보
	 * @return 대기열 정보(대기 순서 정보 포함)
	 */
	public WaitingQueueInfo getWaitingQueueInfo(final String token) {
		final WaitingQueue currentWaitingQueue = waitingQueueReader.getByToken(token);
		return WaitingQueueInfo.of(currentWaitingQueue);
	}

	/**
	 * 가장 오래된 대기열 목록 조회
	 * @param activationCount 조회하려는 활성화 대기열 개수
	 * @return 활성화하려는 대기열 목록
	 */
	public List<WaitingQueue> getWaitingQueuesToBeActivated(final int activationCount) {
		return waitingQueueReader.getWaitingQueuesToBeActivated(activationCount);
	}

	/**
	 * 현재 대기열이 활성화 상태인지 확인. 각 요청 전에 대기열 상태를 활성화 상태인지 확인할 때 사용
	 * @param token 대기열 토큰 정보
	 * @throws CoreException 대기열이 만료되었거나 활성화 상태가 아닌 경우
	 */
	public void activateQueue(final String token) {
		waitingQueueWriter.moveToActiveQueue(token);
	}

	/**
	 * 현재 대기열이 활성화 상태인지 확인. 각 요청 전에 대기열 상태를 활성화 상태인지 확인할 때 사용
	 * @param token 대기열 토큰 정보
	 * @throws CoreException 대기열 토큰이 활성화 상태가 아닌 경우
	 */
	public void checkActivatedQueue(final String token) {
		final WaitingQueue currentWaitingQueue = waitingQueueReader.getActiveQueueByToken(token);

		if (!currentWaitingQueue.isActivated()) {
			waitingQueueWriter.removeActiveQueue(token);
			throw new CoreException(WaitingQueueErrorType.WAITING_QUEUE_NOT_ACTIVATED, "대기열 정보가 활성상태가 아닙니다.");
		}
	}

	/**
	 * 활성 대기열 만료 처리
	 * @param token 대기열 토큰 정보
	 */
	public void expireActiveQueue(final String token) {
		// 1. 현재 활성화된 대기열 만료
		WaitingQueue currentQueue = waitingQueueReader.getActiveQueueByToken(token);
		currentQueue.expire(LocalDateTime.now()); // 만료 시간 설정
		waitingQueueWriter.createWaitingQueue(currentQueue);

		// 2. 다음 대기열 활성화
		List<WaitingQueue> nextQueues = waitingQueueReader.getWaitingQueuesToBeActivated(1);
		if (!nextQueues.isEmpty()) {
			WaitingQueue nextQueue = nextQueues.get(0);
			nextQueue.activate(LocalDateTime.now()); // 활성화 시간 설정
			waitingQueueWriter.createWaitingQueue(nextQueue);
		}
	}

	/**
	 * 대기열이 ACTIVATED로 바뀔 때까지 계속 확인
	 * 스레드가 Interrupted 되면 예외 발생
	 */
	@Transactional(readOnly = true)
	public WaitingQueue waitUntilActivatedNoTimeout(String token) throws InterruptedException {
		while (true) {
			WaitingQueueInfo queue = getWaitingQueueInfo(token);
			if (queue.getStatus() == QueueStatus.ACTIVATED) {
				log.info("대기열 활성화됨. token={}", token);
				return queue.getWaitingQueue();
			}
			// 0.5초 대기 후 재확인
			Thread.sleep(500);
		}
	}
}
