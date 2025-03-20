package com.prgrms.be.intermark.domain.newerd.actor.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.actor.model.ActorTobe;
import com.prgrms.be.intermark.domain.newerd.actor.repository.ActorRepositoryTobe;

@Service
public class ActorServiceTobe {

	private final ActorRepositoryTobe actorRepositoryTobe;

	@Autowired
	public ActorServiceTobe(ActorRepositoryTobe actorRepositoryTobe) {
		this.actorRepositoryTobe = actorRepositoryTobe;
	}

	@Transactional(readOnly = true)
	public ActorTobe findById(Long id) {
		return actorRepositoryTobe.findById(id)
			.orElseThrow(() -> {
				throw new EntityNotFoundException("존재하지 않는 배우입니다");
			});
	}
}
