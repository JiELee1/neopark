package com.prgrms.be.intermark.domain.newerd.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prgrms.be.intermark.domain.newerd.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
