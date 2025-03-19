package com.prgrms.be.intermark.domain.newerd.concert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.concert.model.Concert;
import com.prgrms.be.intermark.domain.newerd.concert.repository.ConcertRepository;

@Service
@Transactional(readOnly = true)
public class ConcertService {

	private final ConcertRepository concertRepository;

	@Autowired
	public ConcertService(ConcertRepository concertRepository) {
		this.concertRepository = concertRepository;
	}

	@Transactional
	public Long save(Concert concert) {
		Concert savedConcert = concertRepository.save(concert);
		return savedConcert.getId();
	}

}
