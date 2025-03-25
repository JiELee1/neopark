package com.prgrms.be.intermark.domain.newerd.stadium.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.stadium.repository.StadiumRepositoryTobe;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumValidationService {

	private final StadiumRepositoryTobe stadiumRepository;

	public void checkIsExist(Long stadiumId) {
		boolean isExist = stadiumRepository.existsById(stadiumId);
		if (!isExist) {
			throw new EntityNotFoundException("해당 공연장은 존재하지 않습니다.");
		}
	}
}
