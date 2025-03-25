package com.prgrms.be.intermark.domain.newerd.castinginfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.actor.service.ActorValidationServiceTobe;
import com.prgrms.be.intermark.domain.newerd.castinginfo.model.CastingInfo;
import com.prgrms.be.intermark.domain.newerd.castinginfo.repository.CastingInfoRepositoryTobe;

@Service
@Transactional(readOnly = true)

public class CastingInfoServiceTobe {

	@Autowired
	private CastingInfoRepositoryTobe castingInfoRepository;

	@Autowired
	private ActorValidationServiceTobe actorValidationService;

	@Autowired
	public CastingInfoServiceTobe(CastingInfoRepositoryTobe castingInfoRepository) {
		this.castingInfoRepository = castingInfoRepository;
	}

	@Transactional
	public void saveAll(List<CastingInfo> castings) {
		checkAllActorsExist(castings);
		castingInfoRepository.saveAll(castings);
	}

	private void checkAllActorsExist(List<CastingInfo> castings) {
		for (CastingInfo casting : castings) {
			actorValidationService.checkIsExist(casting.getActorId());
		}
	}

}
