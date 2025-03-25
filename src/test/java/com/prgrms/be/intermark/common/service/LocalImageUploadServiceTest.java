package com.prgrms.be.intermark.common.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.prgrms.be.intermark.common.dto.ImageResponseDTO;

@SpringBootTest
@ActiveProfiles("h2")
class LocalImageUploadServiceTest {

	@Autowired
	private LocalImageUploadService localImageUploadService;

	@DisplayName("이미지 업로드 시 원본 파일 이름, 저장된 파일 경로를 반환한다.")
	@Test
	void uploadImage() {
		// given
		String subPath = "img/";
		MockMultipartFile multipartFile = new MockMultipartFile(
			"file",                        // @RequestParam 이름
			"test-image.jpg",              // 파일 이름
			MediaType.IMAGE_JPEG_VALUE,    // Content-Type (image/jpeg)
			"fake image content".getBytes() // 이미지 바이트 (테스트용 더미 데이터)
		);

		// when
		ReflectionTestUtils.setField(localImageUploadService, "localRootPath", "./");
		ImageResponseDTO imageResponseDTO = localImageUploadService.uploadImage(multipartFile, subPath);

		// then
		Assertions.assertThat(imageResponseDTO.originalFileName()).isEqualTo("test-image.jpg");
	}

}
