package com.prgrms.be.intermark.domain.newerd.castinginfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.be.intermark.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "casting_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CastingInfo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long concertId;

	private Long actorId;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	@Builder
	public CastingInfo(Long actorId, Long concertId) {
		this.isDeleted = false;
		this.concertId = concertId;
		this.actorId = actorId;
	}

	public void deleteCasting() {
		this.isDeleted = true;
	}

}
