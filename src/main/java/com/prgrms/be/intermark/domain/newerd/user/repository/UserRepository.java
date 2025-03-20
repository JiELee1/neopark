package com.prgrms.be.intermark.domain.newerd.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prgrms.be.intermark.domain.newerd.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdAndDeletedFalse(Long userId);
}
