package com.prgrms.be.intermark.domain.newerd.concert.service;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.actor.model.Gender;
import com.prgrms.be.intermark.domain.newerd.actor.model.ActorTobe;
import com.prgrms.be.intermark.domain.newerd.actor.service.ActorServiceTobe;
import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;
import com.prgrms.be.intermark.domain.newerd.stadium.service.StadiumServiceTobe;
import com.prgrms.be.intermark.domain.user.SocialType;
import com.prgrms.be.intermark.domain.user.User;
import com.prgrms.be.intermark.domain.user.UserRole;

@SpringBootTest
@Transactional
class ConcertFacadeServiceTest {

	private final ConcertService concertService;
	private final ActorServiceTobe actorService;
	private final StadiumServiceTobe stadiumService;

	@Autowired
	public ConcertFacadeServiceTest(ConcertService concertService, ActorServiceTobe actorService,
		StadiumServiceTobe stadiumService) {
		this.concertService = concertService;
		this.actorService = actorService;
		this.stadiumService = stadiumService;
	}

	public static MockMultipartFile createMockFile() {
		return new MockMultipartFile(
			"file",                     // 필드명 (Multipart 요청에서 key 값)
			"test-file.txt",            // 파일 이름
			"text/plain",               // MIME 타입
			"Hello, this is a test file.".getBytes()  // 파일 내용 (바이트 배열)
		);
	}

	@BeforeEach
	void beforeEach() {
		StadiumTobe stadiumTobe = new StadiumTobe("상암월드컵", "서울", "http://sdkfjslkdf.com");
		User user = new User(SocialType.GOOGLE, "abcd", "nickname", UserRole.ROLE_ADMIN, "km1031kim@naver.com");
		ActorTobe actor1 = new ActorTobe("일진규", LocalDate.of(1995, 10, 31), Gender.MALE, "kjgurl");
		ActorTobe actor2 = new ActorTobe("이진규", LocalDate.of(1995, 10, 31), Gender.MALE, "kjgurl");
		ActorTobe actor3 = new ActorTobe("삼진규", LocalDate.of(1995, 10, 31), Gender.MALE, "kjgurl");
	}

	@DisplayName("")
	@Test
	void test() {
		// given
		StadiumTobe stadiumTobe = new StadiumTobe("상암월드컵", "서울", "http://sdkfjslkdf.com");
		User user = new User(SocialType.GOOGLE, "abcd", "nickname", UserRole.ROLE_ADMIN, "km1031kim@naver.com");
		ActorTobe actor1 = new ActorTobe("일진규", LocalDate.of(1995, 10, 31), Gender.MALE, "kjgurl");
		ActorTobe actor2 = new ActorTobe("이진규", LocalDate.of(1995, 10, 31), Gender.MALE, "kjgurl");
		ActorTobe actor3 = new ActorTobe("삼진규", LocalDate.of(1995, 10, 31), Gender.MALE, "kjgurl");

	/*	ConcertCreateRequestDTO concertCreateRequestDTO = new ConcertCreateRequestDTO(
			"캣츠",
			ViewRating.ADULT,
			Genre.DRAMA,
			"캣츠입니다.",
			LocalDate.now(),
			LocalDate.now().plusDays(20),
			50,
			user.getId(),
			stadiumTobe.getId(),
			List.of(1L, 2L, 3L));
*/
		// when

		// then
	}

}
