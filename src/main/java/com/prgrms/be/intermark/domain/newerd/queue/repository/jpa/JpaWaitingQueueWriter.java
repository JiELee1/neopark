// package com.prgrms.be.intermark.domain.newerd.queue.repository.jpa;
//
// import com.prgrms.be.intermark.domain.newerd.queue.exception.CoreException;
// import com.prgrms.be.intermark.domain.newerd.queue.exception.WaitingQueueErrorType;
// import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
// import com.prgrms.be.intermark.domain.newerd.queue.repository.WaitingQueueWriter;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Repository;
//
// import java.time.LocalDateTime;
//
// @Repository
// @RequiredArgsConstructor
//
// public class JpaWaitingQueueWriter implements WaitingQueueWriter {
//
//     private final WaitingQueueWriterJpaRepository waitingQueueWriterJpaRepository;
//
//     /**
//      * 대기열 생성 메서드
//      * - 새로운 대기열을 DB에 추가
//      * - 생성된 대기열은 "WAITING" 상태로 설정
//      */
//     @Override
//     public WaitingQueue createWaitingQueue(final WaitingQueue queueToken) {
//         // 대기열 생성
//         WaitingQueue newQueue = WaitingQueue.builder()
//                 .userId(queueToken.getUserId())
//                 .token(queueToken.getToken())
//                 .status(queueToken.getStatus()) // 초기 상태는 WAITING
//                 .waitingOrder(0L) // 기본 대기 순번
//                 .createdAt(LocalDateTime.now())
//                 .updatedAt(LocalDateTime.now())
//                 .build();
//
//         try {
//             // DB에 저장
//             return waitingQueueWriterJpaRepository.save(newQueue);
//         } catch (Exception e) {
//             throw new CoreException(WaitingQueueErrorType.DB_ERROR, "대기열 생성 중 오류가 발생했습니다.");
//         }
//     }
//
//     /**
//      * 대기열을 활성화된 대기열로 이동
//      * - 대기열을 "ACTIVATED" 상태로 업데이트
//      * - TTL 관련 처리는 애플리케이션에서 처리하도록 하며, JPA에서는 만료 시간을 다루지 않음
//      */
//     @Override
//     public void moveToActiveQueue(final String token) {
//         WaitingQueue queueToActivate;
//         try {
//             // 대기열에서 활성 대기열로 상태 변경
//             queueToActivate = waitingQueueWriterJpaRepository.findByToken(token)
//                     .orElseThrow(() -> new CoreException(WaitingQueueErrorType.WAITING_QUEUE_NOT_FOUND, "해당 대기열을 찾을 수 없습니다."));
//
//             // 상태를 ACTIVATED로 변경
//             queueToActivate.activate(LocalDateTime.now());
//
//             // DB에 변경된 상태 저장
//             waitingQueueWriterJpaRepository.save(queueToActivate);
//         } catch (CoreException e) {
//             throw e;
//         } catch (Exception e) {
//             throw new CoreException(WaitingQueueErrorType.DB_ERROR);
//         }
//     }
//
//
//     /**
//      * 활성 대기열에서 제거
//      * - DB에서 대기열을 삭제
//      */
//     @Override
//     public void removeActiveQueue(final String token) {
//         WaitingQueue queueToRemove;
//         try {
//             // 활성화된 대기열을 DB에서 삭제
//             queueToRemove = waitingQueueWriterJpaRepository.findByToken(token)
//                     .orElseThrow(() -> new CoreException(WaitingQueueErrorType.WAITING_QUEUE_NOT_FOUND, "해당 대기열을 찾을 수 없습니다."));
//
//             // DB에서 삭제
//             waitingQueueWriterJpaRepository.delete(queueToRemove);
//         } catch (CoreException e) {
//             // 예외 발생 시 적절한 에러 메시지 로깅 및 던지기
//             throw e; // 이미 CoreException이므로 그대로 던짐
//         } catch (Exception e) {
//             throw new CoreException(WaitingQueueErrorType.DB_ERROR);
//         }
//     }
// }
//