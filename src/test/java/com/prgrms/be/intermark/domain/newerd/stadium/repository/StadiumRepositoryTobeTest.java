package com.prgrms.be.intermark.domain.newerd.stadium.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.prgrms.be.intermark.domain.newerd.stadium.dto.StadiumResponse;
import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;

@ActiveProfiles("h2")
@SpringBootTest
class StadiumRepositoryTobeTest {

	@Autowired
	private StadiumRepositoryTobe stadiumRepository;

	@AfterEach
	void tearDown() {
		stadiumRepository.deleteAllInBatch();
	}

	@DisplayName("공연장 정보를 받아 공연장 엔티티를 생성한다.")
	@Test
	void create() {
		// given
		StadiumTobe stadium1 = createStadium("서울공연장", "서울");
		StadiumTobe stadium2 = createStadium("광교공연장", "광교");
		StadiumTobe stadium3 = createStadium("제주공연장", "제주");

		// when
		List<StadiumTobe> savedStadiums = stadiumRepository.saveAll(List.of(stadium1, stadium2, stadium3));

		// then
		// 보통 리스트 검증은? 갯수,
		assertThat(savedStadiums).hasSize(3)
			.extracting("name", "address")
			.containsExactlyInAnyOrder(
				Tuple.tuple("서울공연장", "서울"),
				Tuple.tuple("광교공연장", "광교"),
				Tuple.tuple("제주공연장", "제주")
			);
	}

	@DisplayName("공연장 id를 받아 존재 여부를 확인한다.")
	@Test
	void test() {
		// given
		StadiumTobe stadium = createStadium("서울공연장", "서울");

		StadiumTobe savedStadium = stadiumRepository.save(stadium);

		// when
		boolean resultShouldBeTrue = stadiumRepository.existsById(savedStadium.getId());
		boolean resultShouldBeFalse = stadiumRepository.existsById(2L);

		// then
		assertThat(resultShouldBeTrue).isTrue();
		assertThat(resultShouldBeFalse).isFalse();
	}

	// 테스트에 필요한 파라미터만 받고, 나머지는 기본값으로
	private StadiumTobe createStadium(String name, String address) {
		return StadiumTobe.builder()
			.name(name)
			.address(address)
			.imageUrl("/tmp")
			.build();
	}

	@Test
	@DisplayName("공연장 정보 페이징 처리")
	void page() {
		// given
		StadiumTobe stadium1 = createStadium("서울공연장", "서울");
		StadiumTobe stadium2 = createStadium("광교공연장", "광교");
		StadiumTobe stadium3 = createStadium("제주공연장", "제주");

		stadiumRepository.saveAll(List.of(stadium1, stadium2, stadium3));

		// Pageable 구현체를 주로 PageRequest를 사용한다.
		PageRequest pageRequest = PageRequest.of(0, 3);

		//when
		Page<StadiumTobe> page = stadiumRepository.findAll(pageRequest);

		// dto로 변환. map()으로 내부를 다르게 만든다.
		Page<StadiumResponse> toMap = page.map(stadiumTobe -> StadiumResponse.of(stadium1));
		// 이제 api로 반환해도 된다.

		// 실제 가져오는건 아래와 같이 가져오면 됨. 추가적인 정보가 필요없으면
		// List<StadiumTobe> page = stadiumRepository.findAll(pageRequest);

		//then

		// 반환 타입이 페이지면 토탈 쿼리를 자동으로 날린다.
		// 셀렉트 + 카운트 쿼리 날라감.
		List<StadiumTobe> content = page.getContent(); // 내부 데이터 꺼내기
		long totalElements = page.getTotalElements();

		assertThat(content).hasSize(3);
		assertThat(page.getTotalElements()).isEqualTo(3);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isFalse();

		/**
		 * 토탈 카운트 때문에 페이징 성능이 느려짐.
		 * 토탈 카운트 튜닝이 필요함.
		 */
	}
}
