package com.prgrms.be.intermark.domain.newerd.stadium.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.common.dto.page.PageListIndexSize;
import com.prgrms.be.intermark.common.dto.page.PageResponseDTO;
import com.prgrms.be.intermark.common.exception.domain.stadium.DuplicatedStadiumException;
import com.prgrms.be.intermark.domain.newerd.stadium.dto.StadiumCreateServiceRequest;
import com.prgrms.be.intermark.domain.newerd.stadium.dto.StadiumResponse;
import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;
import com.prgrms.be.intermark.domain.newerd.stadium.repository.StadiumRepositoryTobe;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumServiceTobe {

	private final StadiumRepositoryTobe stadiumRepository;

	/**
	 * [FEAT] : 공연장 생성 로직 추가
	 */
	@Transactional
	public StadiumResponse create(StadiumCreateServiceRequest request) {

		checkDuplicateAddress(request);
		checkDuplicateName(request);

		StadiumTobe stadium = request.toEntity();
		StadiumTobe savedStadium = stadiumRepository.save(stadium);

		return StadiumResponse.of(savedStadium);
	}

	private void checkDuplicateAddress(StadiumCreateServiceRequest request) {
		if (stadiumRepository.existsByAddress(request.getAddress())) {
			throw new DuplicatedStadiumException("address", request.getAddress());
		}
	}

	private void checkDuplicateName(StadiumCreateServiceRequest request) {
		if (stadiumRepository.existsByName(request.getName())) {
			throw new DuplicatedStadiumException("name", request.getName());
		}
	}

	public boolean isNotExist(Long stadiumId) {
		return !stadiumRepository.existsById(stadiumId);
	}

	public StadiumResponse findStadiumById(Long stadiumId) {
		StadiumTobe stadiumTobe = stadiumRepository.findById(stadiumId)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 공연장입니다."));

		return StadiumResponse.of(stadiumTobe);
	}

	public Page<StadiumResponse> findAll(@PageableDefault(size = 10, sort = "stadiumId") Pageable pageable) {
		/**
		 * 무조건 DTO로 변환해서 넘겨야 한다.
		 */
		Page<StadiumTobe> pages = stadiumRepository.findAll(pageable);
		return pages.map(StadiumResponse::of);

	}

	public PageResponseDTO<StadiumTobe, StadiumResponse> findAllStadiums(Pageable pageable) {

		Page<StadiumTobe> stadiumTobePage = stadiumRepository.findAll(pageable);
		return new PageResponseDTO<>(stadiumTobePage, StadiumResponse::of, PageListIndexSize.STADIUM_LIST_INDEX_SIZE);

	}
}
