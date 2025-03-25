package com.prgrms.be.intermark.common.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prgrms.be.intermark.common.dto.ImageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalImageUploadService implements ImageUploadService {

	@Value("${local.images.path}")
	private String localRootPath;

	@Override
	public ImageResponseDTO uploadImage(MultipartFile multipartFile, String subPath) {

		// 비어있다면 에외처리. 예외메세지 제대로, 예외도 적절하게 만들기
		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException("이미지가 없습니다.");
		}

		// 원본파일이름
		String originalFilename = multipartFile.getOriginalFilename();

		// 원본파일로부터 저장 시 파일 이름 만들기
		String savedFileName = createSavedFileName(originalFilename);

		// 파일저장경로 추출
		String savedFileLocalPath = getSavedFileLocalPath(subPath, savedFileName);

		// 파일저장
		saveFile(multipartFile, savedFileLocalPath);

		return ImageResponseDTO.builder()
			.originalFileName(originalFilename)
			.path(savedFileLocalPath)
			.build();
	}

	@Override
	public void saveFile(MultipartFile multipartFile, String savedFileLocalPath) {
		File uploadImage = new File(savedFileLocalPath);
		try {
			multipartFile.transferTo(uploadImage);
		} catch (IOException e) {
			throw new IllegalArgumentException("이미지를 업로드할 수 없습니다.. " + e.getMessage());
		}
	}

	@Override
	public List<ImageResponseDTO> uploadImages(List<MultipartFile> multipartFiles, String subPath) {
		return multipartFiles.stream()
			.map(multipartFile -> uploadImage(multipartFile, subPath))
			.toList();
	}

	@Override
	public ImageResponseDTO getExpectedImageInfo(MultipartFile multipartFile, String subPath) {
		// 비어있다면 에외처리. 예외메세지 제대로, 예외도 적절하게 만들기
		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException("이미지가 없습니다.");
		}

		// 원본파일이름
		String originalFilename = multipartFile.getOriginalFilename();

		// 원본파일로부터 저장 시  파일 이름 만들기
		String savedFileName = createSavedFileName(originalFilename);

		// 파일저장경로 추출
		String savedFileLocalPath = getSavedFileLocalPath(subPath, savedFileName);

		return ImageResponseDTO.builder()
			.originalFileName(originalFilename)
			.path(savedFileLocalPath)
			.build();
	}

	@Override
	public List<ImageResponseDTO> getExpectedImagesInfo(List<MultipartFile> multipartFiles, String subPath) {
		return multipartFiles.stream()
			.map(multipartFile -> getExpectedImageInfo(multipartFile, subPath))
			.toList();
	}

	private String createSavedFileName(String originalFileName) {
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + extractExtension(originalFileName);
	}

	private String extractExtension(String originalFileName) {
		int beforeExtensionIndex = originalFileName.lastIndexOf(".");
		return originalFileName.substring(beforeExtensionIndex + 1);
	}

	private String getSavedFileLocalPath(String subPath, String savedFileName) {
		return localRootPath + subPath + savedFileName;
	}
}
