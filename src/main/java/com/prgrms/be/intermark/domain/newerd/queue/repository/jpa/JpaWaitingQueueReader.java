// package com.prgrms.be.intermark.domain.newerd.queue.repository.jpa;
//
// import com.prgrms.be.intermark.domain.newerd.queue.exception.CoreException;
// import com.prgrms.be.intermark.domain.newerd.queue.exception.WaitingQueueErrorType;
// import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
// import com.prgrms.be.intermark.domain.newerd.queue.model.enums.QueueStatus;
// import com.prgrms.be.intermark.domain.newerd.queue.repository.WaitingQueueReader;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.stereotype.Repository;
//
// import java.util.List;
//
// @Slf4j
// @Repository
// @RequiredArgsConstructor
//
// public class JpaWaitingQueueReader implements WaitingQueueReader {
//
//     private final WaitingQueueReaderJpaRepository waitingQueueReaderJpaRepository;
//
//     /**
//      * 토큰으로 대기열 조회
//      * - 대기열 상태(QueueStatus.WAITING)인 항목 중
//      *   해당 엔티티의 createdAt 이전에 생성된 레코드 수를 구해 순번을 대체할 수 있습니다.
//      * - 활성(QueueStatus.ACTIVATED) 상태라면 waitingOrder를 0으로 세팅합니다.
//      * - 그 외 상태(EXPIRED 등)라면 Redis 구현에 맞춰 예외를 던지도록 구성할 수 있습니다.
//      */
//     @Override
//     public WaitingQueue getByToken(final String token) {
//         // DB에서 토큰으로 조회
//         WaitingQueue foundQueue = waitingQueueReaderJpaRepository.findByToken(token)
//                 .orElseThrow(() -> new CoreException(WaitingQueueErrorType.WAITING_QUEUE_NOT_FOUND));
//
//         // WAITING 상태라면 순번(대기열에서의 위치)을 구해 세팅
//         if (foundQueue.isWaiting()) {
//             long rank = waitingQueueReaderJpaRepository.countByCreatedAtBeforeAndStatus(
//                     foundQueue.getCreatedAt(),
//                     QueueStatus.WAITING
//             );
//
//             // rank는 0부터 시작하지 않도록, 필요에 따라 +1 등의 처리를 해도 됌.
//             return WaitingQueue.builder()
//                     .id(foundQueue.getId())
//                     .userId(foundQueue.getUserId())
//                     .token(foundQueue.getToken())
//                     .waitingOrder(rank)
//                     .status(foundQueue.getStatus())
//                     .activatedAt(foundQueue.getActivatedAt())
//                     .expiredAt(foundQueue.getExpiredAt())
//                     .lastActionedAt(foundQueue.getLastActionedAt())
//                     .createdAt(foundQueue.getCreatedAt())
//                     .updatedAt(foundQueue.getUpdatedAt())
//                     .build();
//         }
//
//         // ACTIVATED 상태라면 waitingOrder를 0으로 세팅
//         if (foundQueue.isActivated()) {
//             return WaitingQueue.builder()
//                     .id(foundQueue.getId())
//                     .userId(foundQueue.getUserId())
//                     .token(foundQueue.getToken())
//                     .waitingOrder(0L)
//                     .status(foundQueue.getStatus())
//                     .activatedAt(foundQueue.getActivatedAt())
//                     .expiredAt(foundQueue.getExpiredAt())
//                     .lastActionedAt(foundQueue.getLastActionedAt())
//                     .createdAt(foundQueue.getCreatedAt())
//                     .updatedAt(foundQueue.getUpdatedAt())
//                     .build();
//         }
//
//         // 그 외 상태(EXPIRED 등)는 예외 처리
//         throw new CoreException(WaitingQueueErrorType.WAITING_QUEUE_NOT_FOUND);
//     }
//
//     /**
//      * 활성화 대상이 될 대기열 목록 조회
//      * - WAITING 상태이며 오래된 순으로 정렬하여 상위 activationCount개만 조회
//      */
//     @Override
//     public List<WaitingQueue> getWaitingQueuesToBeActivated(final int activationCount) {
//         // WAITING 상태 정렬 후 상위 N건 조회
//         // PageRequest.of(0, activationCount) 를 사용하여 LIMIT 기능 구현
//         return waitingQueueReaderJpaRepository.findByStatusOrderByCreatedAtAsc(
//                 QueueStatus.WAITING,
//                 PageRequest.of(0, activationCount)
//         );
//     }
//
//     /**
//      * 활성 상태 대기열 조회
//      * - 토큰으로 조회 후, 활성 상태면 해당 엔티티, 아니면 EXPIRED 처리
//      */
//     @Override
//     public WaitingQueue getActiveQueueByToken(final String token) {
//         // 존재하지 않으면 토큰만 세팅 후 EXPIRED 상태 반환
//         WaitingQueue foundQueue = waitingQueueReaderJpaRepository.findByToken(token)
//                 .orElseGet(() -> WaitingQueue.builder()
//                         .token(token)
//                         .status(QueueStatus.EXPIRED)
//                         .build());
//
//         // 이미 활성 상태면 그대로 반환, 아니면 만료 상태로 간주
//         if (foundQueue.isActivated()) {
//             return foundQueue;
//         }
//
//         // EXPIRED 상태로 새 객체 반환
//         return WaitingQueue.builder()
//                 .token(token)
//                 .status(QueueStatus.EXPIRED)
//                 .build();
//     }
// }