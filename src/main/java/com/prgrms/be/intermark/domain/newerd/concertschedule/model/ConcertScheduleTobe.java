package com.prgrms.be.intermark.domain.newerd.concertschedule.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.be.intermark.common.entity.BaseEntity;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ConcertScheduleTobe extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;

	private boolean deleted;

	private Long concertId;

	private Long stadiumId;

	@Builder
	private ConcertScheduleTobe(LocalDateTime startTime, LocalDateTime endTime, Long concertId, Long stadiumId) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.deleted = false;
		this.concertId = concertId;
		this.stadiumId = stadiumId;
	}

	public ConcertScheduleResponse createResponse() {
		return ConcertScheduleResponse.builder()
			.id(id)
			.concertId(concertId)
			.stadiumId(stadiumId)
			.startTime(startTime)
			.endTime(endTime)
			.build();
	}
}
