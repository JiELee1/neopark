package com.prgrms.be.intermark.domain.newerd.queue.repository.redis;

import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
import com.prgrms.be.intermark.domain.newerd.queue.repository.WaitingQueueWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class RedisWaitingQueueWriter implements WaitingQueueWriter {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String WAITING_QUEUE_KEY = "waiting-queues";
    private static final String ACTIVE_QUEUE_KEY = "active-queues";
    private static final String TOKEN_META_KEY_PREFIX = "token-meta:";

    private static final long ACTIVE_QUEUE_TTL_SECONDS = 300; // 5분

    @Override
    public WaitingQueue createWaitingQueue(final WaitingQueue queueToken) {
        final String token = queueToken.getToken();
        long timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();

        // 대기열 생성
        redisTemplate.opsForZSet().add(WAITING_QUEUE_KEY, token, timestamp);
        // 대기열 메타 정보 저장
        redisTemplate.opsForHash().put(TOKEN_META_KEY_PREFIX + token, "userId", String.valueOf(queueToken.getUserId()));

        return queueToken;
    }

    @Override
    public void moveToActiveQueue(final String token) {
        // 대기열에서 제거
        redisTemplate.opsForZSet().remove(WAITING_QUEUE_KEY, token);
        // 활성화 대기열에 추가
        redisTemplate.opsForSet().add(ACTIVE_QUEUE_KEY, token);
        // 대기열 메타 정보에 TTL 설정
        redisTemplate.expire(TOKEN_META_KEY_PREFIX + token, Duration.ofSeconds(ACTIVE_QUEUE_TTL_SECONDS));
    }

    @Override
    public void removeActiveQueue(final String token) {
        // 활성화 대기열에서 제거
        redisTemplate.opsForSet().remove(ACTIVE_QUEUE_KEY, token);
        // 대기열 메타 정보 삭제
        redisTemplate.delete(TOKEN_META_KEY_PREFIX + token);
    }
}