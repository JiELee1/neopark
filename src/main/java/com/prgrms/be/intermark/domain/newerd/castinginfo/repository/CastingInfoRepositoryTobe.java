package com.prgrms.be.intermark.domain.newerd.castinginfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prgrms.be.intermark.domain.newerd.actor.dto.ActorResponse;
import com.prgrms.be.intermark.domain.newerd.castinginfo.model.CastingInfo;

@Repository
public interface CastingInfoRepositoryTobe extends JpaRepository<CastingInfo, Long> {

	@Query("""
		    SELECT new com.prgrms.be.intermark.domain.newerd.actor.dto.ActorResponse(
		        a.id, a.name,  a.profileImageUrl, a.birth, a.gender
		    )
		    FROM ActorTobe a
		    WHERE a.id IN (
		        SELECT c.actorId FROM CastingInfo c WHERE c.concertId = :concertId
		    )
		""")
	List<ActorResponse> findCastingInfosByConcertId(@Param("concertId") Long concertId);

}
