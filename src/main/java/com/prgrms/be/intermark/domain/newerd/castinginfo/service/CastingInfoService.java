package com.prgrms.be.intermark.domain.newerd.castinginfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.actor.repository.ActorRepository;
import com.prgrms.be.intermark.domain.newerd.castinginfo.model.CastingInfo;
import com.prgrms.be.intermark.domain.newerd.castinginfo.repository.CastingInfoRepository;

@Service
@Transactional(readOnly = true)
public class CastingInfoService {

	private final CastingInfoRepository castingInfoRepository;
	private final ActorRepository actorRepository;

	@Autowired
	public CastingInfoService(CastingInfoRepository castingInfoRepository, ActorRepository actorRepository) {
		this.castingInfoRepository = castingInfoRepository;
		this.actorRepository = actorRepository;
	}

	@Transactional
	public void save(List<CastingInfo> castings) {
		castingInfoRepository.saveAll(castings);
	}
/*
	public void update(List<Long> actorIds, Musical musical) {

		castingRepository.deleteByMusical(musical);

		actorIds
			.forEach(actorId -> {
				Actor actor = actorRepository.findById(actorId)
					.orElseThrow(() -> {
						throw new EntityNotFoundException("존재하지 않는 배우입니다");
					});

				Casting casting = Casting.builder()
					.actor(actor)
					.musical(musical)
					.build();
				casting.setActor(actor);
				casting.setMusical(musical);

				castingInfoRepository.save(casting);
			});
	}*/
/*
	@Transactional
	public void deleteAllByMusical(Musical musical) {
		castingRepository.findByMusicalAndIsDeletedIsFalse(musical)
			.forEach(Casting::deleteCasting);
	}*/
}
