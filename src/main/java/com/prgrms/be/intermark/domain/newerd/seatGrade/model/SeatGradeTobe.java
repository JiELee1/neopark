package com.prgrms.be.intermark.domain.newerd.seatGrade.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "seat_grade")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SeatGradeTobe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 10)
	private String name;

	@Positive
	@Column(nullable = false)
	private int price;

	@NotBlank
	@Column(name = "seat_id")
	private Long seatId;

}
