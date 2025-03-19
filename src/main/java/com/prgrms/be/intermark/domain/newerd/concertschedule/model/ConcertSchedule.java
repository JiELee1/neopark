package com.prgrms.be.intermark.domain.newerd.concertschedule.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.prgrms.be.intermark.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ConcertSchedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@NotNull
	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	private Long concertId;

	private Long stadiumId;

	@Builder
	public ConcertSchedule(LocalDateTime startTime, LocalDateTime endTime, Long concertId, Long stadiumId) {
		// TODO : 처리 방법 생각해야할듯
		this.startTime = startTime;
		this.endTime = endTime;
		this.isDeleted = false;
		this.concertId = concertId;
	}

	public boolean isOver(LocalDateTime time) {
		return this.endTime.isBefore(time);
	}

	public void setScheduleTime(LocalDateTime startTime, LocalDateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public void deleteSchedule() {
		isDeleted = true;
	}
}
