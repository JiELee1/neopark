package com.prgrms.be.intermark.domain.newerd.concert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertDetailImage;
import com.prgrms.be.intermark.domain.newerd.concert.repository.ConcertDetailImageRepository;

@Service
@Transactional(readOnly = true)
public class ConcertDetailImageService {

	private final ConcertDetailImageRepository concertDetailImageRepository;

	@Autowired
	public ConcertDetailImageService(ConcertDetailImageRepository concertDetailImageRepository) {
		this.concertDetailImageRepository = concertDetailImageRepository;
	}

	@Transactional
	public void saveDetailImages(List<ConcertDetailImage> concertDetailImages) {
		concertDetailImageRepository.saveAll(concertDetailImages);
	}
}
