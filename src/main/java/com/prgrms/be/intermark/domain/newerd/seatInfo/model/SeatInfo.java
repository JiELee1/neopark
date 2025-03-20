package com.prgrms.be.intermark.domain.newerd.seatInfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "seat_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeatInfo {

	@Id
	@Column(name = "seat_id")
	private Long seatId;

	@NonNull
	@Column(name = "concert_schedule_id", nullable = false)
	private Long concertScheduleId;

	@NonNull
	@Column(name = "concert_id", nullable = false)
	private Long concertId;

	@NonNull
	@Column(name = "stadium_id", nullable = false)
	private Long stadiumId;

	@NonNull
	@Column(name = "seat_grade_id", nullable = false)
	private Long seatGradeId;

	@NonNull
	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "row_num")
	private int rowNum;

	@Column(name = "column_num")
	private int columnNum;

	@Column(name = "is_reserved")
	private boolean isReserved = false; // 기본값을 false로 설정


}
