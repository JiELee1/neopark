package com.prgrms.be.intermark.domain.newerd.queue.repository.redis;

import com.prgrms.be.intermark.domain.newerd.queue.exception.CoreException;
import com.prgrms.be.intermark.domain.newerd.queue.exception.WaitingQueueErrorType;
import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
import com.prgrms.be.intermark.domain.newerd.queue.model.enums.QueueStatus;
import com.prgrms.be.intermark.domain.newerd.queue.repository.WaitingQueueReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisWaitingQueueReader implements WaitingQueueReader {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String WAITING_QUEUE_KEY = "waiting-queues";
    private static final String ACTIVE_QUEUE_KEY = "active-queues";
    private static final String TOKEN_META_KEY_PREFIX = "token-meta:";

    @Override
    public WaitingQueue getByToken(final String token) {
        Long rank = redisTemplate.opsForZSet().rank(WAITING_QUEUE_KEY, token);

        if (rank != null) { // 대기열에 있는 경우
            return buildWaitingQueue(token, rank, QueueStatus.WAITING);
        }

        if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(ACTIVE_QUEUE_KEY, token))) { // 활성열에 있는 경우
            return buildWaitingQueue(token, 0L, QueueStatus.ACTIVATED);
        }

        throw new CoreException(WaitingQueueErrorType.WAITING_QUEUE_NOT_FOUND);
    }

    private WaitingQueue buildWaitingQueue(final String token, final Long order, final QueueStatus status) {
        final String userId = (String) redisTemplate.opsForHash().get(TOKEN_META_KEY_PREFIX + token, "userId");
        if (userId == null) {
            throw new CoreException(WaitingQueueErrorType.WAITING_QUEUE_NOT_FOUND);
        }

        return WaitingQueue.builder()
                .token(token)
                .userId(Long.parseLong(userId))
                .waitingOrder(order)
                .status(status)
                .build();
    }

    public List<WaitingQueue> getWaitingQueuesToBeActivated(final int activationCount) {
        final Set<Object> range = redisTemplate.opsForZSet().range(WAITING_QUEUE_KEY, 0, activationCount - 1);

        if (range == null || range.isEmpty()) {
            return Collections.emptyList();
        }

        // null 값에 대해 음수 인덱스를 부여하기 위한 카운터
        AtomicLong negativeCounter = new AtomicLong(0);

        return range.stream()
                .map(token -> {
                    final String userId = (String) redisTemplate.opsForHash().get(TOKEN_META_KEY_PREFIX + token, "userId");
                    long parsedUserId;
                    if (userId == null || "null".equals(userId)) {
                        parsedUserId = -negativeCounter.incrementAndGet();
                        log.warn("Redis key {} has null or invalid userId: {}. Setting to {}.", TOKEN_META_KEY_PREFIX + token, userId, parsedUserId);
                    } else {
                        try {
                            parsedUserId = Long.parseLong(userId);
                        } catch (NumberFormatException e) {
                            parsedUserId = -negativeCounter.incrementAndGet();
                            log.error("Failed to parse userId {} for token {}. Setting to {}.", userId, token, parsedUserId, e);
                        }
                    }

                    return WaitingQueue.builder()
                            .token((String) token)
                            .userId(parsedUserId)
                            .status(QueueStatus.WAITING)
                            .build();
                })
                .collect(Collectors.toList());
    }


    @Override
    public WaitingQueue getActiveQueueByToken(final String token) {
        final Boolean isActiveQueue = redisTemplate.opsForSet().isMember(ACTIVE_QUEUE_KEY, token);
        final Map<Object, Object> entries = redisTemplate.opsForHash().entries(TOKEN_META_KEY_PREFIX + token);

        final QueueStatus status = Boolean.TRUE.equals(isActiveQueue) && !entries.isEmpty() ? QueueStatus.ACTIVATED : QueueStatus.EXPIRED;

        return WaitingQueue.builder()
                .token(token)
                .status(status)
                .build();
    }

}