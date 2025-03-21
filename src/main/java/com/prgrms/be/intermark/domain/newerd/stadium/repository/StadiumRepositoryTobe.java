package com.prgrms.be.intermark.domain.newerd.stadium.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prgrms.be.intermark.domain.newerd.stadium.model.StadiumTobe;

@Repository
public interface StadiumRepositoryTobe extends JpaRepository<StadiumTobe, Long> {
	boolean existsByAddress(String address);

	boolean existsByName(String name);

	boolean findByIdNot(Long id);

	// 카운트 쿼리에 조인이 나가지 않도록 주의. 지금같은상황은 사실 필요없음.
	@Query(value = "select s from StadiumTobe s", countQuery = "select count(s.id) from StadiumTobe s")
	Page<StadiumTobe> findAll(Pageable pageable);
}
