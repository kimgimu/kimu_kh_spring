package com.kh.restapi.twoproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;	// RestAPI용 컨트롤러 >> 데이터를 반환(json 형식으로)

import com.kh.restapi.twoproject.dto.ArticleForm;
import com.kh.restapi.twoproject.entity.Article;
import com.kh.restapi.twoproject.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ArticleApiController {
	
	// ArticleService 객체의 Bean을 자동으로 주입받는다.
	// -> DI (Dependency Injection)
	@Autowired
	private ArticleService articleService;
	
	// get - 글 전체 조회
	@GetMapping("/api/articles")
	public List<Article> index() {
		log.info("ArticleApiController의 index() 실행");
		
		// service의 index() 호출해서 DB 처리 결과 받는다.
		return articleService.index();
	}
		
	// post
	// 하나 글 조회
	@GetMapping("api/articles/{id}")
	public Article show(@PathVariable Long id) {
		log.info("ArticleApiController의 show() 실행");
		
		return articleService.show(id);
	}

	@PostMapping("/api/articles")
	public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
		log.info("ArticleApiController의 create() 실행");
		log.info(dto.toString());
		
		Article saved = articleService.create(dto);
		
		// 모두 정상적으로 입력받고 저장 완료한다는 전제하에!
		// 혹시라도 데이터 빼고 들어오거나 에러발생할 수 있는 경우도 처리해야함
		// 에러발생으로 body부분이 빈 경우 build() 메서드 이용해서 body 없이 넘김
		return saved != null ?
				ResponseEntity.status(HttpStatus.CREATED).body(saved) : 
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
	}
	// patch
	// 수정
	@PatchMapping("/api/articles/{id}")
	public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
		log.info("ArticleApiController의 update() 실행");
		log.info("수정할 게시글의 id = " + id);
		log.info(dto.toString());
		
		Article article = dto.toEntity();	// 수정하기 위해 입력한 데이터
		// 수정할 Entity 조회
//		
//		 Article target = this.articleRepository.findById(article.getId()).orElse(null);
//
//		    if (target != null) {
//		      articleRepository.save(article);
//		    }
		
		Article updated = articleService.update(id,dto);

		
		return updated != null ?
				ResponseEntity.status(HttpStatus.OK).body(updated) : 
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	// delete
	// 글번호를 이용해서 삭제
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Article> delete(@PathVariable Long id) {
		log.info("ArticleApiController의 delete() 실행");
		log.info("삭제할 게시글의 id = " + id);
		
		// 삭제 데이터 확인하고 null 아니면 정상적으로 삭제 되었다고 선택해서 돌려준다.
		Article deleted = articleService.delete(id);
		
		return deleted != null ?
				ResponseEntity.status(HttpStatus.OK).build() : 
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
