package com.prgrms.be.intermark.domain.stadium.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.stadium.model.Stadium;
import com.prgrms.be.intermark.domain.stadium.repository.StadiumRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumService {

	private final StadiumRepository stadiumRepository;

	public Stadium findById(Long stadiumId) {
		return stadiumRepository.findById(stadiumId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException("존재하지 않는 공연장입니다");
			});
	}

	public boolean isExistId(Long stadiumId) {
		return stadiumRepository.existsById(stadiumId);
	}
}
