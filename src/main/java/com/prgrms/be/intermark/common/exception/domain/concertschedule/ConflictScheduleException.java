package com.prgrms.be.intermark.common.exception.domain.concertschedule;

import java.time.LocalDateTime;

public class ConflictScheduleException extends RuntimeException {

	public ConflictScheduleException(LocalDateTime startDatetime, LocalDateTime endDateTime) {
		super("해당 공연장의 " + startDatetime + " : " + endDateTime + " 시간대에 이미 다른 공연이 존재합니다.");
	}
}
