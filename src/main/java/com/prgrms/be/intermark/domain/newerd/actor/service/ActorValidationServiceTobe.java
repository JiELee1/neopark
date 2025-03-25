package com.prgrms.be.intermark.domain.newerd.actor.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.actor.model.ActorTobe;
import com.prgrms.be.intermark.domain.newerd.actor.repository.ActorRepositoryTobe;

@Service
@Transactional(readOnly = true)
public class ActorValidationServiceTobe {

	@Autowired
	private ActorRepositoryTobe actorRepository;

	public ActorTobe findActiveActor(Long actorId) {
		return actorRepository.findById(actorId)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 배우입니다"));
	}

	public void checkIsExist(Long actorId) {
		boolean isExist = actorRepository.existsById(actorId);

		if (!isExist) {
			throw new EntityNotFoundException("[배우 아이디 : " + actorId + "] 해당 배우는 존재하지 않습니다");
		}
	}
}
