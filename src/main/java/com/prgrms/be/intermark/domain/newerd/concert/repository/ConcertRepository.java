package com.prgrms.be.intermark.domain.newerd.concert.repository;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prgrms.be.intermark.domain.newerd.concert.model.ConcertTobe;

@Repository
public interface ConcertRepository extends JpaRepository<ConcertTobe, Long> {

	boolean existsConcertsByTitle(@NotBlank String title);

}
