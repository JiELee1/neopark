package com.prgrms.be.intermark.domain.newerd.actor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prgrms.be.intermark.domain.newerd.actor.repository.ActorRepositoryTobe;

@Service
public class ActorServiceTobe {

	private final ActorRepositoryTobe actorRepository;

	@Autowired
	public ActorServiceTobe(ActorRepositoryTobe actorRepository) {
		this.actorRepository = actorRepository;
	}

}
