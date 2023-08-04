package com.kh.restapi.twoproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.restapi.twoproject.dto.CommentDto;
import com.kh.restapi.twoproject.service.CommentSerivce;

import lombok.extern.slf4j.Slf4j;

// controller : url들어오면 컨트롤러가 관리해준다.
//              뷰 화면 보여주는 컨트롤러

// json 타입의 데이터를 주고 받는 controller
@RestController
@Slf4j
public class CommentApiController {

	// 실제 데이터베이스를 접근하기 위해서
	// 서비스한테 일을 보내야된다.
	@Autowired
	private CommentSerivce commentSerivce; // 새로운변수를 만든다.

	// 댓글을 관리하는 컨트롤러
	// 목록 조회 요청
	// rest api는 결과를 보낼때 응답 코드와 데이터를 같이 보내야된다.
	// ResponeEntity
	// talend로 확인시 http://localhost:9080/api/comments/4/comments

	@GetMapping("/api/comments/{articleId}/comments")
	public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {

		log.info("CommentApiController comments()메서드 실행");
		log.info("articleId {}", articleId);

		// 서비스에게 위임(처리하는 과정을 맡긴다)
		List<CommentDto> dtos = commentSerivce.comments(articleId);

		// 결과 응답 (상태 코드만 넘기는것이 아니라 , 실제 데이터)
		return ResponseEntity.status(HttpStatus.OK).body(dtos);

	}
	// body  데이터를 보내기 생성
//	{
//		"nickname" : "hong",
//		"body" : "슈룹",
//		"article_id" : 5
//	}


	// 댓글 생성 요청
	@PostMapping("/api/comments/{articleId}/comments")
	public ResponseEntity<CommentDto> create
							(@PathVariable Long articleId,
							@RequestBody CommentDto dto) {

		log.info("CommentApiController create()메서드 실행");
		log.info("articleId {}", articleId);
		log.info("dto {}", dto);

		// 서비스에게 위임(처리하는 과정을 맡긴다)
		CommentDto createDto = commentSerivce.create(articleId,dto);

		// 결과 응답 (상태 코드만 넘기는것이 아니라 , 실제 데이터)
		return ResponseEntity.status(HttpStatus.OK).body(createDto);
	}

	// 댓글 수정 요청

	// 댓글 삭제 요청

}
