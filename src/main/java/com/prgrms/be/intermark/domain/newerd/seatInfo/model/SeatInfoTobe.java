package com.prgrms.be.intermark.domain.newerd.seatInfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeatInfoTobe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "concert_schedule_id", nullable = false)
	private Long concertScheduleId;

	@Column(name = "concert_id", nullable = false)
	private Long concertId;

	@Column(name = "stadium_id", nullable = false)
	private Long stadiumId;

	@Column(name = "seat_grade_id", nullable = false)
	private Long seatGradeId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Positive
	@Column(name = "row_num")
	private int rowNum;

	@Positive
	@Column(name = "column_num")
	private int columnNum;

	@Column(name = "is_reserved")
	private boolean reserved = false; // 기본값을 false로 설정

	public void reserve() {
		this.reserved = true;
	}

	public void cancel() {
		this.reserved = false;
	}
}
