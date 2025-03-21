package com.prgrms.be.intermark.domain.newerd.stadium.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.prgrms.be.intermark.common.exception.domain.stadium.DuplicatedStadiumException;
import com.prgrms.be.intermark.domain.newerd.stadium.dto.StadiumCreateServiceRequest;
import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;
import com.prgrms.be.intermark.domain.newerd.stadium.repository.StadiumRepositoryTobe;

@ActiveProfiles("h2")
@SpringBootTest
class StadiumServiceTobeTest {

	@Autowired
	private StadiumRepositoryTobe stadiumRepository;

	@Autowired
	private StadiumServiceTobe stadiumService;

	@AfterEach
	void tearDown() {
		stadiumRepository.deleteAllInBatch();
	}

	@DisplayName("공연장 이름은 유니크 값이여야 한다. 중복되는 경우 저장에 실패한다.")
	@Test
	void duplicateNameTest() {
		// given
		StadiumTobe stadium1 = createStadium("서울공연장", "서울");
		StadiumTobe stadium2 = createStadium("서울공연장", "광교");
		stadiumRepository.save(stadium1);

		// when / then
		Assertions.assertThatThrownBy(() -> stadiumRepository.save(stadium2))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@DisplayName("공연장 주소는 유니크 값이여야 한다. 중복되는 경우 저장에 실패한다.")
	@Test
	void duplicateAddressTest() {
		// given
		StadiumTobe stadium1 = createStadium("서울공연장", "서울");
		StadiumTobe stadium2 = createStadium("광교공연장", "서울");
		stadiumRepository.save(stadium1);

		// when / then
		Assertions.assertThatThrownBy(() -> stadiumRepository.save(stadium2))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@DisplayName("공연장 이름 및 주소는 유니크 값이여야 한다. 중복되는 경우 저장에 실패한다.")
	@Test
	void duplicateNameAndAddressTest() {
		// given
		StadiumTobe stadium1 = createStadium("서울공연장", "서울");
		StadiumTobe stadium2 = createStadium("서울공연장", "광교");
		stadiumRepository.save(stadium1);

		// when / then
		Assertions.assertThatThrownBy(() -> stadiumRepository.save(stadium2))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("공연장 생성 시 중복되는 이름, 중복되는 주소가 있는 경우 DuplicatedStadiumException 를 반환한다. ")
	void createDuplicateStadiumTest() {

		// given
		StadiumTobe stadium1 = createStadium("서울공연장", "서울");
		stadiumRepository.save(stadium1);

		StadiumCreateServiceRequest duplicateNameRequest = createStadiumRequest("서울공연장", "광교", "/tmp");
		StadiumCreateServiceRequest duplicateAddressRequest = createStadiumRequest("광교공연장", "서울", "/tmp");
		StadiumCreateServiceRequest duplicateAddressAndNameRequest = createStadiumRequest("서울공연장", "서울", "/tmp");

		// when / then
		Assertions.assertThatThrownBy(() -> stadiumService.create(duplicateNameRequest))
			.isInstanceOf(DuplicatedStadiumException.class);

		Assertions.assertThatThrownBy(() -> stadiumService.create(duplicateAddressRequest))
			.isInstanceOf(DuplicatedStadiumException.class);

		Assertions.assertThatThrownBy(() -> stadiumService.create(duplicateAddressAndNameRequest))
			.isInstanceOf(DuplicatedStadiumException.class);
	}

	// 테스트에 필요한 파라미터만 받고, 나머지는 기본값으로
	@DisplayName("존재하지 않은 공연장ID 를 isNotExist 메서드 인자로 전달할 경우 true 를 반환한다.")
	@Test
	void isNotExist() {

		// given
		StadiumTobe stadium1 = createStadium("서울공연장", "서울");
		StadiumTobe savedStadium = stadiumRepository.save(stadium1);

		// when
		boolean shouldBeFalse = stadiumService.isNotExist(savedStadium.getId());
		boolean shouldBeTrue = stadiumService.isNotExist(2L);

		// then
		Assertions.assertThat(shouldBeFalse).isFalse();
		Assertions.assertThat(shouldBeTrue).isTrue();
	}

	private StadiumTobe createStadium(String name, String address) {
		return StadiumTobe.builder()
			.name(name)
			.address(address)
			.imageUrl("/tmp")
			.build();
	}

	private StadiumCreateServiceRequest createStadiumRequest(String name, String address, String imageUrl) {
		return StadiumCreateServiceRequest.builder()
			.name(name)
			.address(address)
			.imageUrl(imageUrl)
			.build();
	}

}
