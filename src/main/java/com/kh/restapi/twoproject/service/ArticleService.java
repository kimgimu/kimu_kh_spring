package com.kh.restapi.twoproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.restapi.twoproject.dto.ArticleForm;
import com.kh.restapi.twoproject.entity.Article;
import com.kh.restapi.twoproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

// DB 접근해서 결과 만드는 곳
// 컨트롤러에서 DB접근 해야되면 서비스한테 넘겨주고
// 넘겨준 데이터 가지고 실제 DB 접근해서 데이터 가져옴

@Slf4j	// DB 로그 확인
@Service
public class ArticleService {

	// ArticleRepository 인터페이스 객체의 Bean을 자동으로 주입받는다.
	@Autowired
	private ArticleRepository articleRepository;
	
	// Article 전체 목록 조회
	public List<Article> index() {
		log.info("ArticleService의 index() 실행");
		
		return articleRepository.findAll();
	}
	
	// 단건 조회
	public Article show(Long id) {
		log.info("ArticleService의 show() 실행");
		
		return articleRepository.findById(id).orElse(null);	// data없으면 null 처리
	}
	
	// 생성
	public Article create(ArticleForm dto) {
		log.info("ArticleService의 create() 실행");
		Article article = dto.toEntity();
		
		// id는 DB가 자동 생성하므로 id가 넘어오는 데이터를 저장하지 않는다.
		if(article.getId() != null) {
			return null;
		}
		return articleRepository.save(article);
	}
	
	// 수정
	public Article update(Long id, ArticleForm dto) {
		log.info("ArticleService의 update() 실행");
		Article article = dto.toEntity();
		Article target = articleRepository.findById(id).orElse(null);
		
		// 수정 대상이 없거나 id가 다른 경우 
		// 잘못된 요청이다. (400에러) - 처리 필요
		if(target == null || id != article.getId()) {
			log.info("잘못된 요청! id: {}, article: {}",id, article.toString());
			return null;
		}
		// 수정할 title이나 수정할 content 입력 되었니 
		// 수정할 대상이 있는 필드들을 새로 저장해주기!
		// patch()메서드가 한다. 
		target.patch(article);
		return articleRepository.save(target);
	}
	
	// 삭제
	public Article delete(Long id) {
		log.info("ArticleService의 delete() 실행");
		Article target = articleRepository.findById(id).orElse(null);
		
		if(target == null) {
			log.info("잘못된 요청! {} 번 글은 테이블에 존재하지 않아요.",id);
			return null;
		}
		articleRepository.delete(target);
		return target;
	}
	
	// 트랜잭션(Transaction)
	//  - 데이터베이스의 상태를 변화시키는 하나의 논리적 기능을 수행하기 위한 작업 단위
	//  - 사용자가 시스템에 대한 서비스 요구 시 시스템이 응답하기 위한 상태 변환 과정의 작업 단위
	//  - 여러 sql 문들을 단일작업으로 묶어서 나줘질 수 없게 만드는 것이 트랙잭션 
	//  - 만약 실행 중에 중간에 실패하면 내용을 싹 날리고 처음으로 돌아간다.
	
	//  ex) 계좌이체 
	//  - commit 또는 rollback 
	
	// @Transactional 어노테이션을 이용해서 메서드를 묶는다.
	// 데이터를 추가할 때 문제가 발생하면 추가한 내용을 가지고 게시판에 
	// 전체 출력을 할 때 문제가 발생하면 실행내용을 모두 취소하고 
	// 기존에 내용으로 돌아간다 
	
	// 정상적으로 실행하면 데이터를 영구적으로 저장까지 할 수있도록 
	// 만들어주는 것이 트랜잭션 처리방법이다.
	
	@Transactional
	public List<Article> createArticles(List<ArticleForm> dtos){
		
		log.info("ArticleService의 createArticles메서드 실행");
		
		
		//stream()
		// 자바8부터  추가된 자바 스트림
		//  추가된 컬렉션의 저장 요소를 하나씩 참조해 람다식으로 
		//  처리를 할 수있도록 해주는 반복자 
		
		// 람다식 (코드를 간결하게 사용) 
		
		// 스트림의 특징 
		//  데이터소스를 변경하지 않음 (읽기모드)
		//  일회성 스트림도 요소를 모두 읽고 나면 닫혀서 사용할 수 없다.
		//  필요하다면 새 스트림을 생성해서 사용한다.
		//  내부적으로 반복 처리함 
		
		// toList()
		// toMap()
		// toSet()
		//  stream의 요소를 수집하여 요소를 그룹화 하거나 
		//   결과를 담아반환하는데 사용한다. 
		
		// stream()
		//  스트림 생성: 스트림 인스턴스 생성
		//   중간 연산 : 필터링 및 매핑을 통해서 얻고자 하는 데이터로 가공하는 중간 작업
		//   최종 연산 : 최종 결과를 만들어내는 작업 
		
		// Arrays.stream(dtos) 
		
		List<Article> articleList = dtos.stream()
				.map(dto -> dto.toEntity())
				.collect(Collectors.toList());
		
		log.info("articleList:{}", articleList);
		
		articleList.stream()
		.forEach(article->articleRepository.save(article));
		
		// 강제 예외발생 
		// 데이터베이스 처리 과정에서 예외가 발생할 경우에는 
		// orElseThrow() 메서드를 이용해서 
		// 예외를 처리한다. 
		//  -1L 아이디 값들이 마이너스가 나올 수는 없다!
		//  일부러 예외를 발생 ! 
		
		articleRepository.findById(-1L).orElseThrow(
			() -> new IllegalArgumentException("예외처리메시지") 	
		);
		
		/*
		 * 아이디를 이용해서 데이터를 저장 
		 * 전체적인 글의 내용을 저장 
		 * 
		// dto 묶음을 entity묶으로 변환하는 작업 
		List<Article> articleList = new ArrayList<>();
		for(int i = 0; i < dtos.size(); i++) {
			Article entity = dtos.get(i).toEntity();
			articleList.add(entity);		
		
		}
		// entity 묶음을 DB로 저장한다. 
		for(int i = 0; i < articleList.size(); i++) {
			
			articleRepository.save(articleList.get(i));
		
		}*/
		
		return articleList;
		
	}	
}

/*
 * 
 * Test 
 *  프로그램의 품질 검증을 위한 것!
 *  우리가 의도대로 프로그램이 동작하는지 확인 하는 것!
 * 
 * TDD (Test Driven Development)
 *  소프트웨어 개발시 먼저 테스트를 작성하고 그 다음에 코드를 작성하는 것을 강조 
 *  코드 작성전에 먼저 테스트해서 코드를 작성하면 코드의 품질과 안정성을 높일 수있다.
 * 
 * Junit
 * 
 * 
 */





