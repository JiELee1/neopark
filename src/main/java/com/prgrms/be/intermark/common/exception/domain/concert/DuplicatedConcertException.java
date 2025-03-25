package com.prgrms.be.intermark.common.exception.domain.concert;

public class DuplicatedConcertException extends RuntimeException {

	public DuplicatedConcertException(String title) {
		super(String.format(
			"이미 등록되어있는 공연입니다. 제목: [%s]", title)
		);
	}
}
