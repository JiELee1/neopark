package com.prgrms.be.intermark.domain.newerd.concert.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.common.exception.domain.concert.DuplicatedConcertException;
import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertTobe;
import com.prgrms.be.intermark.domain.newerd.concert.repository.ConcertRepository;

@Service
@Transactional(readOnly = true)
public class ConcertValidationService {

	@Autowired
	private ConcertRepository concertRepository;

	public void checkSameTitleIsExist(String title) {
		boolean exists = concertRepository.existsConcertsByTitle(title);
		if (exists) {
			throw new DuplicatedConcertException(title);
		}
	}

	public ConcertTobe findActiveConcertById(Long concertId) {
		return concertRepository.findById(concertId).orElseThrow(
			() -> new EntityNotFoundException("존재하지 않는 공연입니다."));
	}
}
