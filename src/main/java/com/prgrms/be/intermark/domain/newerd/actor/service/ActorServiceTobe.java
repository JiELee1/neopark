package com.prgrms.be.intermark.domain.newerd.actor.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.actor.model.ActorTobe;
import com.prgrms.be.intermark.domain.newerd.actor.repository.ActorRepositoryTobe;

@Service
public class ActorServiceTobe {

	private final ActorRepositoryTobe actorRepository;

	@Autowired
	public ActorServiceTobe(ActorRepositoryTobe actorRepository) {
		this.actorRepository = actorRepository;
	}

	@Transactional(readOnly = true)
	public ActorTobe findById(Long id) {
		return actorRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 배우입니다"));
	}

	public ActorTobe findActorTobeByActorUidAndName(Long uid, String name) {
		return actorRepository.findActorTobeByActorUidAndName(uid, name)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 배우입니다."));
	}
}
