package com.prgrms.be.intermark.domain.newerd.booking.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookingHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "seat_id", nullable = false)
	private Long seatId;

	@Column(name = "concert_schedule_id", nullable = false)
	private Long concertScheduleId;

	@Column(name = "concert_id", nullable = false)
	private Long concertId;

	@Column(name = "stadium_id", nullable = false)
	private Long stadiumId;

	@Column(name = "seat_grade_id", nullable = false)
	private Long seatGradeId;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private BookingStatus status;

}
