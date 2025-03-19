package com.prgrms.be.intermark.domain.newerd.actor.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.actor.model.Actor;
import com.prgrms.be.intermark.domain.newerd.actor.repository.ActorRepository;

@Service
public class ActorService {

	private final ActorRepository actorRepository;

	@Autowired
	public ActorService(ActorRepository actorRepository) {
		this.actorRepository = actorRepository;
	}

	@Transactional(readOnly = true)
	public Actor findById(Long id) {
		return actorRepository.findById(id)
			.orElseThrow(() -> {
				throw new EntityNotFoundException("존재하지 않는 배우입니다");
			});
	}
}
