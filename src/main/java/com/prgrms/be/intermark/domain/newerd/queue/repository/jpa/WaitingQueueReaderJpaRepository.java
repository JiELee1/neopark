// package com.prgrms.be.intermark.domain.newerd.queue.repository.jpa;
// import com.prgrms.be.intermark.domain.newerd.queue.model.entity.WaitingQueue;
// import com.prgrms.be.intermark.domain.newerd.queue.model.enums.QueueStatus;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// 
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;
//
// public interface WaitingQueueReaderJpaRepository extends JpaRepository<WaitingQueue, Long> {
// 
//     Optional<WaitingQueue> findByToken(String token);
// 
//     /**
//      * 특정 시간(createdAt) 이전에 WAITING 상태인 레코드 수를 구해 순번(rank)을 구하기 위한 예시 메서드
//      */
//     @Query("SELECT COUNT(w) FROM WaitingQueue w " +
//             "WHERE w.status = :status " +
//             "AND w.createdAt < :createdAt")
//     long countByCreatedAtBeforeAndStatus(@Param("createdAt") LocalDateTime createdAt,
//                                          @Param("status") QueueStatus status);
// 
//     /**
//      * WAITING 상태이며, 생성 시간이 오래된 순으로 레코드를 조회
//      */
//     List<WaitingQueue> findByStatusOrderByCreatedAtAsc(QueueStatus status, Pageable pageable);
// }