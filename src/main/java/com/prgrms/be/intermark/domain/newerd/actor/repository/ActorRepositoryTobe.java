package com.prgrms.be.intermark.domain.newerd.actor.repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prgrms.be.intermark.domain.newerd.actor.model.ActorTobe;

@Repository
public interface ActorRepositoryTobe extends JpaRepository<ActorTobe, Long> {
	Optional<ActorTobe> findActorTobeByName(String name);

	Optional<ActorTobe> findActorTobeByActorUidAndName(@NotBlank Long actorUid, @NotBlank String name);
}
