package com.prgrms.be.intermark.common.exception.domain.concertschedule;

import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertResponse;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleCreateServiceRequest;

public class InAvailableScheduleException extends RuntimeException {

	public InAvailableScheduleException(ConcertResponse concert, ConcertScheduleCreateServiceRequest schedule) {
		super(String.format(
			"공연 [%s]의 공연 기간은 %s ~ %s 이며, 현재 등록하려는 일정은 해당 공연 가능 기간을 벗어났습니다. (시작: %s, 종료: %s, 공연장 ID: %d)",
			concert.getTitle(),
			concert.getStartDate(),
			concert.getEndDate(),
			schedule.getStartTime(),
			schedule.getEndTime(),
			schedule.getStadiumId()
		));
	}
}
