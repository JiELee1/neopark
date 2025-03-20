package com.prgrms.be.intermark.domain.newerd.seatGrade.model;

import com.prgrms.be.intermark.domain.musical.model.Musical;
import com.prgrms.be.intermark.domain.musical_seat.model.MusicalSeat;
import com.prgrms.be.intermark.domain.schedule_seat.model.ScheduleSeat;
import com.prgrms.be.intermark.domain.ticket.model.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "seat_grade")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SeatGrade {

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
