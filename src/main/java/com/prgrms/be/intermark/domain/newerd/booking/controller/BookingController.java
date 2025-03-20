package com.prgrms.be.intermark.domain.newerd.booking.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.be.intermark.common.dto.ApiStatus;
import com.prgrms.be.intermark.common.dto.ResponseDTO;
import com.prgrms.be.intermark.domain.newerd.booking.dto.ReserveConcertRequest;
import com.prgrms.be.intermark.domain.newerd.booking.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/api/v2/bookings")
@RequiredArgsConstructor
@RestController
public class BookingController {

	private final BookingService bookingService;

	@PostMapping
	public ResponseEntity<ResponseDTO<?>> reserveConcert(@RequestBody @Valid ReserveConcertRequest reserveConcertRequest){
		bookingService.reserveConcert(reserveConcertRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.builder().status(ApiStatus.SUCCESS).build());
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<ResponseDTO<?>> cancelConcert(@AuthenticationPrincipal User user, @PathVariable Long bookingId){
		bookingService.cancelConcert(Long.valueOf(user.getUsername()), bookingId);
		return ResponseEntity.ok().body(ResponseDTO.builder().status(ApiStatus.SUCCESS).build());
	}

}
