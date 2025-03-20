package com.prgrms.be.intermark.domain.newerd.user.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.prgrms.be.intermark.domain.newerd.user.model.User;
import com.prgrms.be.intermark.domain.newerd.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

	private final UserRepository userRepository;

	public User findActiveUser(Long userId) {
		return userRepository.findByIdAndDeletedFalse(userId)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않은 유저입니다."));
	}


}
