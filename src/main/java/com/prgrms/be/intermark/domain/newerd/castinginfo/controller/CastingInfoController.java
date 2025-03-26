package com.prgrms.be.intermark.domain.newerd.castinginfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.be.intermark.common.dto.ResponseDTO;
import com.prgrms.be.intermark.domain.newerd.actor.dto.ActorResponse;
import com.prgrms.be.intermark.domain.newerd.castinginfo.service.CastingInfoServiceTobe;

@RestController
@RequestMapping("/api/v2/castingInfo")
public class CastingInfoController {

	@Autowired
	private CastingInfoServiceTobe castingInfoServiceTobe;

	@GetMapping("/{concertId}")
	public ResponseDTO<List<ActorResponse>> findActorsByConcertId(@PathVariable("concertId") Long concertId) {
		List<ActorResponse> castingActors = castingInfoServiceTobe.findActorsBy(concertId);
		return ResponseDTO.success(castingActors);
	}
}
