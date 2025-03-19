package com.prgrms.be.intermark.domain.actor.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.actor.model.Actor;
import com.prgrms.be.intermark.domain.actor.repository.ActorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActorService {

	private final ActorRepository actorRepository;

	// TODO : 배우 저장 기능 구현 필요

	@Transactional(readOnly = true)
	public Actor findById(Long id) {
		return actorRepository.findById(id)
			.orElseThrow(() -> {
				throw new EntityNotFoundException("존재하지 않는 배우입니다");
			});
	}
}
