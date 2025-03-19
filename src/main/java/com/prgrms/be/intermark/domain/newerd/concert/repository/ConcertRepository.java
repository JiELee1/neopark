package com.prgrms.be.intermark.domain.newerd.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prgrms.be.intermark.domain.newerd.concert.model.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

}
