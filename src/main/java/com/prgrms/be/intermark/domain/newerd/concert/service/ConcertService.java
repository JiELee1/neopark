package com.prgrms.be.intermark.domain.newerd.concert.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.prgrms.be.intermark.common.dto.ImageResponseDTO;
import com.prgrms.be.intermark.common.service.ImageUploadService;
import com.prgrms.be.intermark.domain.newerd.actor.model.ActorTobe;
import com.prgrms.be.intermark.domain.newerd.actor.service.ActorValidationServiceTobe;
import com.prgrms.be.intermark.domain.newerd.castinginfo.model.CastingInfo;
import com.prgrms.be.intermark.domain.newerd.castinginfo.service.CastingInfoServiceTobe;
import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertCreateServiceRequest;
import com.prgrms.be.intermark.domain.newerd.concert.dto.ConcertResponse;
import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertDetailImage;
import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertTobe;
import com.prgrms.be.intermark.domain.newerd.concert.repository.ConcertRepository;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleResponse;
import com.prgrms.be.intermark.domain.newerd.concertschedule.service.ConcertScheduleService;
import com.prgrms.be.intermark.domain.newerd.user.service.UserValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {

	private static final String THUMBNAIL_PATH = "img/thumbnail/";
	private static final String DETAIL_IMAGES_PATH = "img/detailImages/";

	private final ConcertRepository concertRepository;

	private final ImageUploadService imageUploadService;
	private final ConcertDetailImageService concertDetailImageService;
	private final CastingInfoServiceTobe castingInfoServiceTobe;

	private final ConcertValidationService concertValidationService;
	private final UserValidationService userValidationService;
	private final ActorValidationServiceTobe actorValidationService;
	private final ConcertScheduleService concertScheduleService;

	@Transactional
	public Long create(ConcertCreateServiceRequest concertCreateRequest,
		MultipartFile thumbnail,
		List<MultipartFile> detailImages
	) {

		// 등록 유저 존재 학인
		checkUserIsExist(concertCreateRequest.managerId());

		// 엔티티 필드 값을 채워주기 위해 파일 full path 를 미리 가져옴.
		ImageResponseDTO expectedThumbnailInfo = imageUploadService.getExpectedImageInfo(thumbnail, THUMBNAIL_PATH);
		List<ImageResponseDTO> expectedDetailImagesInfo =
			imageUploadService.getExpectedImagesInfo(detailImages, DETAIL_IMAGES_PATH);

		// 콘서트 저장
		Long concertId = saveConcert(
			ConcertTobe.createWithThumbnailPath(concertCreateRequest, expectedThumbnailInfo.path()));

		// 콘서트 상세 이미지 저장
		saveConcertDetailsImages(expectedDetailImagesInfo, concertId);

		// 배우는 casting_info 테이블에 저장
		saveActorsToCastingInfo(concertCreateRequest, concertId);

		// 엔티티 처리 시 문제가 없는 경우에만 이미지파일 로컬에 저장.
		saveThumbnailToLocal(thumbnail, expectedThumbnailInfo);
		saveDetailImagesToLocal(detailImages, expectedDetailImagesInfo);
		return concertId;
	}

	private void checkUserIsExist(Long managerId) {
		userValidationService.checkIsExist(managerId); // 결론 좋았다..
	}

	private void saveConcertDetailsImages(List<ImageResponseDTO> detailImagesInfo, Long concertId) {
		concertDetailImageService.saveDetailImages(getConcertDetailImages(detailImagesInfo, concertId));
	}

	private void saveActorsToCastingInfo(ConcertCreateServiceRequest concertCreateRequest, Long concertId) {
		List<CastingInfo> castingInfoList = mapActorToCastingInfo(concertCreateRequest, concertId);
		castingInfoServiceTobe.saveAll(castingInfoList);
	}

	private Long saveConcert(ConcertTobe concert) {
		checkAvailableToSave(concert);

		ConcertTobe savedConcert = concertRepository.save(concert);
		return savedConcert.getId();
	}

	private void checkAvailableToSave(ConcertTobe concert) {
		concertValidationService.checkSameTitleIsExist(concert.getTitle());
	}

	private List<CastingInfo> mapActorToCastingInfo(ConcertCreateServiceRequest concertCreateRequest, Long concertId) {
		return concertCreateRequest.concertActorRegisterDTOS()
			.stream()
			.map(actorInfo -> {
				// TODO : 배우가 없다면 디비에 저장하고 처리...
				ActorTobe actor = actorValidationService.findActiveActor(actorInfo.actorId());
				return CastingInfo.builder()
					.actorId(actor.getId())
					.concertId(concertId)
					.build();
			})
			.toList();
	}

	private static List<ConcertDetailImage> getConcertDetailImages(
		List<ImageResponseDTO> detailImagesInfo, Long concertId) {
		return ConcertDetailImage.fromImagesAndConcertId(detailImagesInfo, concertId);
	}

	private void saveThumbnailToLocal(MultipartFile thumbnail, ImageResponseDTO expectedThumbnailInfo) {
		imageUploadService.saveFile(thumbnail, expectedThumbnailInfo.path());
	}

	private void saveDetailImagesToLocal(List<MultipartFile> detailImages,
		List<ImageResponseDTO> expectedDetailImagesInfo) {
		for (ImageResponseDTO imageResponseDTO : expectedDetailImagesInfo) {
			for (MultipartFile detailImage : detailImages) {
				if (imageResponseDTO.originalFileName().equals(detailImage.getOriginalFilename())) {
					saveThumbnailToLocal(detailImage, imageResponseDTO);
				}
			}
		}
	}

	public ConcertResponse findByConcertId(Long concertId) {
		ConcertTobe concert = concertValidationService.findActiveConcertById(concertId);
		return concert.createResponse();
	}

	public Page<ConcertResponse> findAllPages(Pageable pageable) {
		Page<ConcertTobe> concertPages = concertRepository.findAll(pageable);
		return concertPages.map(ConcertTobe::createResponse);
	}

	public Page<ConcertScheduleResponse> findSchedulesByConcertId(Long concertId, Pageable pageable) {
		return concertScheduleService.findSchedulesByConcertId(concertId, pageable);
	}
}
