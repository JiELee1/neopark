package com.prgrms.be.intermark.domain.newerd.queue.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.be.intermark.auth.TokenProvider;
import com.prgrms.be.intermark.domain.newerd.queue.controller.dto.CreateWaitingQueueResponse;
import com.prgrms.be.intermark.domain.newerd.queue.controller.dto.GetWaitingQueueStatusResponse;
import com.prgrms.be.intermark.domain.newerd.queue.model.dto.WaitingQueueInfo;
import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
import com.prgrms.be.intermark.domain.newerd.queue.uscase.CreateWaitingQueueUseCase;
import com.prgrms.be.intermark.domain.newerd.queue.uscase.GetWaitingQueueUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/waiting-queues")
@RequiredArgsConstructor
// XXX: (사용 안할 예정) -> WaitingQueueController에서 좌석 예매가 이루어지지는 않음.
public class WaitingQueueController {
	private final CreateWaitingQueueUseCase createWaitingQueueUseCase;
	private final GetWaitingQueueUseCase getWaitingQueueUseCase;
	private final TokenProvider tokenProvider;

	@PostMapping
	public ResponseEntity<CreateWaitingQueueResponse> createWaitingQueue(
		@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
	) {
		String token = extractToken(authorizationHeader);
		final WaitingQueue waitingQueue = createWaitingQueueUseCase.createWaitingQueueId(
			Long.valueOf(tokenProvider.getUserIdFromAccessToken(
				token
			)));

		return ResponseEntity.status(201)
			.body(CreateWaitingQueueResponse.of(waitingQueue));
	}

	@GetMapping
	public ResponseEntity<GetWaitingQueueStatusResponse> getWaitingQueue(
		@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
	) {
		final WaitingQueueInfo waitingQueueInfo = getWaitingQueueUseCase.getWaitingQueueInfo(
			extractToken(authorizationHeader));
		return ResponseEntity.ok(GetWaitingQueueStatusResponse.of(waitingQueueInfo));
	}

	/**
	 * Authorization 헤더에서 토큰을 추출합니다.
	 *
	 * @param authorizationHeader Authorization 헤더 값
	 * @return 추출된 토큰
	 */
	private String extractToken(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new IllegalArgumentException("유효하지 않은 토큰 형식입니다");
		}
		return authorizationHeader.substring(7);
	}
}
