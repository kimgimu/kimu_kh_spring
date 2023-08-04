package com.kh.restapi.twoproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

//@Controller	// 뷰페이지로 연결만
@RestController	// 실제 데이터들을 반환 (간혹 json 문자반환도 함)
@Slf4j			// 롬복이 지원하는 로그레벨
public class FirstApiController {
	
	@GetMapping("/api/hello")
	public String hello() {
		log.info("FirstApiController의 hello() 메서드 실행");
		return "hello world";
	}
}

//헤더 : 편지봉투(앞면..?), 바디 : 편지봉투에 담긴 내용물 (비유!)