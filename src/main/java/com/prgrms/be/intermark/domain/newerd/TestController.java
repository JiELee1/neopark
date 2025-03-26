package com.prgrms.be.intermark.domain.newerd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "에제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("/test")
public class TestController {

	@Operation(summary = "요약입니다.", description = "테스트 설명입니다.")
	@Parameter(name = "파라미터 이름", description = "파라미터 설명")
	@GetMapping()
	public String test() {
		return "hello~";
	}
}
