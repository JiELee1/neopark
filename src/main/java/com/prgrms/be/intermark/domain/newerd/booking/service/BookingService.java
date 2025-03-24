package com.prgrms.be.intermark.domain.newerd.booking.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.booking.dto.ReserveConcertRequest;
import com.prgrms.be.intermark.domain.newerd.booking.model.BookingHistory;
import com.prgrms.be.intermark.domain.newerd.booking.repository.BookingHistoryRepository;
import com.prgrms.be.intermark.domain.newerd.concertschedule.service.ConcertScheduleValidationService;
import com.prgrms.be.intermark.domain.newerd.seatInfo.model.SeatInfoTobe;
import com.prgrms.be.intermark.domain.newerd.seatInfo.service.SeatInfoValidationService;
import com.prgrms.be.intermark.domain.newerd.user.service.UserValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

	private final UserValidationService userValidationService;
	private final SeatInfoValidationService seatInfoValidationService;
	private final ConcertScheduleValidationService concertScheduleValidationService;
	private final BookingHistoryRepository bookingHistoryRepository;
	private final BookingValidationService bookingValidationService;

	private final RedissonClient redissonClient;

	@Transactional
	public Long reserveConcert(ReserveConcertRequest reserveConcertRequest) {
		SeatInfoTobe seatInfoTobe = seatInfoValidationService.findAvailableSeatInfo(reserveConcertRequest.seatId());
		//userValidationService.findActiveUser(reserveConcertRequest.userId());
		//concertScheduleValidationService.findAvailableConcertSchedule(seatInfoTobe.getConcertScheduleId());
		//seatInfoTobe.reserve();

		try {
			decrease(reserveConcertRequest.seatId(), seatInfoTobe);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return bookingHistoryRepository.save(reserveConcertRequest.toBookingHistory(seatInfoTobe)).getId();
	}

	public void decrease(Long id, SeatInfoTobe seatInfoTobe) throws InterruptedException {
		// RedissonClient을 활용하여 Lock 객체 조회
		RLock rLock = redissonClient.getLock(id.toString());

		long waitTime = 5L;
		long leaseTime = 3L;
		TimeUnit timeUnit = TimeUnit.SECONDS;

		try {
			boolean available = rLock.tryLock(waitTime, leaseTime, timeUnit);

			if (!available) {
				return;
			}

			//락 획득 후 로직 수행
			seatInfoTobe.reserve();
		} catch (InterruptedException e) {
			//락을 얻으려고 시도하다가 인터럽트를 받았을 때 발생하는 예외
			log.error("Interrupted while trying to acquire lock", e);
		} finally {
			try {
				rLock.unlock();
				log.info("unlock complete: {}", rLock.getName());
			} catch (IllegalMonitorStateException e) {
				//이미 종료된 락일 때 발생하는 예외
				log.error("Lock already released", e);
			}
		}
	}

	@Transactional
	public void cancelConcert(Long userId, Long historyId) {
		BookingHistory bookingHistory = bookingValidationService.findBookingHistory(historyId, userId);
		SeatInfoTobe seatInfoTobe = seatInfoValidationService.findReservedSeatInfo(bookingHistory.getSeatId());

		seatInfoTobe.cancel();
		bookingHistory.cancel();
	}

}
