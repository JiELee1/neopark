package com.prgrms.be.intermark.domain.newerd.concertschedule.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.be.intermark.domain.newerd.concert.model.Concert;
import com.prgrms.be.intermark.domain.newerd.concert.repository.ConcertRepository;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleCreateRequestDTO;
import com.prgrms.be.intermark.domain.newerd.concertschedule.dto.ConcertScheduleUpdateRequestDTO;
import com.prgrms.be.intermark.domain.newerd.concertschedule.model.ConcertSchedule;
import com.prgrms.be.intermark.domain.newerd.concertschedule.repository.ConcertScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertScheduleService {

	// TODO : ERD가 변경됨에 따라 내부 로직이 달라져서, 기능 구현은 필요 시 진행하면 될 듯 합니다.

	private final ConcertScheduleRepository concertScheduleRepository;

	// TODO : 해당 공연장에서 일정 생성 가능한지 체크 필요, 서비스에 의존할지 레포지토리에 의존할지 결정해야 할듯.

	private ConcertRepository concertRepository;

	@Transactional
	public Long createSchedule(ConcertScheduleCreateRequestDTO requestDto) {
		Concert concert = concertRepository.findById(requestDto.concertId())
			.orElseThrow(() -> new EntityNotFoundException("해당 공연이 존재하지 않습니다."));

		// TODO : 공연 일정을 설정할 수 있는지 확인. 쿼리 재설정 필요.
	/*	int duplicatedSchedulesNum = concertScheduleRepository.getSchedulesNumByStartTime(
			requestDto.getStartTime(),
			requestDto.getEndTime(concert),
			concert.getStadiumId());
		if (duplicatedSchedulesNum > 0) {
			throw new IllegalStateException("해당 시작 시간에 이미 다른 스케줄이 존재합니다.");
		}*/

		// TODO : 일정 생성이 가능하다면 ConcertSchedule 엔티티 생성
		//ConcertSchedule schedule = concertScheduleRepository.save(공연);

		// 기존코드
		//Schedule schedule = scheduleRepository.save(requestDto.toEntity(musical));

		return concert.getId();
	}

	@Transactional
	public void updateSchedule(Long scheduleId, ConcertScheduleUpdateRequestDTO requestDto) {
		// TODO : 공연일정 수정 로직 변경
		ConcertSchedule schedule = concertScheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new EntityNotFoundException("해당 스케줄이 존재하지 않습니다."));

		if (schedule.isDeleted()) {
			throw new EntityNotFoundException("해당 스케줄이 존재하지 않습니다.");
		}

		//
		/*LocalDateTime startTime = requestDto.getStartTime();
		LocalDateTime endTime = requestDto.getEndTime(schedule.getC());

		int duplicatedSchedulesNum = scheduleRepository.getDuplicatedScheduleExceptById(
			scheduleId,
			startTime,
			endTime,
			schedule.getMusical().getStadium());
		if (duplicatedSchedulesNum > 0) {
			throw new IllegalStateException("해당 시작 시간에 이미 다른 스케줄이 존재합니다.");
		}

		schedule.setScheduleTime(startTime, endTime);*/
	}

	@Transactional
	public void deleteSchedule(Long scheduleId) {
		ConcertSchedule schedule = concertScheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new EntityNotFoundException("해당 스케줄이 존재하지 않습니다."));

		//List<Ticket> tickets = schedule.getTickets().stream().filter((Ticket::isReserved)).toList();

	/*	if (tickets.size() > 0) {
			throw new IllegalStateException("예매된 스케줄은 삭제할 수 없습니다.");
		}

		if (schedule.isDeleted()) {
			throw new EntityNotFoundException("이미 삭제된 스케줄입니다.");
		}*/

		schedule.deleteSchedule();
	}

	/*@Transactional(readOnly = true)
	public ScheduleSeatResponseDTOs findScheduleSeats(Long scheduleId) {
		List<ScheduleSeatResponseDTO> scheduleSeats
			= scheduleSeatRepository.findAllByScheduleId(scheduleId)
			.stream()
			.map(ScheduleSeatResponseDTO::from)
			.toList();

		return ScheduleSeatResponseDTOs.builder()
			.scheduleSeats(scheduleSeats)
			.build();
	}

	@Transactional(readOnly = true)
	public ScheduleFindResponseDTO findSchedule(Long scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException("존재하지 않는 스케줄입니다.");
			});

		return ScheduleFindResponseDTO.from(schedule);
	}

	@Transactional(readOnly = true)
	public PageResponseDTO<Schedule, ScheduleFindResponseDTO> findSchedulesByMusical(Long musicalId,
		Pageable pageable) {
		Musical musical = musicalRepository.findById(musicalId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException("존재하지 않는 뮤지컬입니다.");
			});

		Page<Schedule> schedulePage = scheduleRepository.findAllByMusical(musical, pageable);

		return new PageResponseDTO<>(
			schedulePage,
			ScheduleFindResponseDTO::from,
			PageListIndexSize.SCHEDULE_LIST_INDEX_SIZE
		);
	}

	@Transactional
	public void deleteAllByMusical(Musical musical) {
		scheduleRepository.findByMusicalAndIsDeletedIsFalse(musical)
			.forEach(Schedule::deleteSchedule);

	}

	public boolean existsByMusical(Musical musical) {
		return scheduleRepository.existsByMusicalAndIsDeletedFalse(musical);
	}*/
}
