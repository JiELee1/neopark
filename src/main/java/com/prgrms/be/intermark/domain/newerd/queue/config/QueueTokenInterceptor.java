package com.prgrms.be.intermark.domain.newerd.queue.config;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.prgrms.be.intermark.domain.newerd.queue.exception.CoreException;
import com.prgrms.be.intermark.domain.newerd.queue.exception.WaitingQueueErrorType;
import com.prgrms.be.intermark.domain.newerd.queue.service.WaitingQueueService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QueueTokenInterceptor implements HandlerInterceptor {

	private final WaitingQueueService waitingQueueService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		final String token = request.getHeader("QUEUE-TOKEN");
		if (Objects.isNull(token) || token.isEmpty()) {
			throw new CoreException(WaitingQueueErrorType.WAITING_QUEUE_HEADER_NOT_FOUND, "대기열 토큰 정보를 찾을 수 없습니다");
		}

		waitingQueueService.checkActivatedQueue(token);
		return true;
	}
}
