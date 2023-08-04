package com.kh.restapi.twoproject.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.restapi.twoproject.dto.ArticleForm;
import com.kh.restapi.twoproject.entity.Article;

// 인텔리j 는 테스트하고 싶은 메서드 우클릭 -> generate -> test

@SpringBootTest //어노테이션을 붙여 스프링 부트와 연동한 통합 테스트를 수행한다.
class ArticleServiceTest {

	@Autowired // ArticleService를 DI 
	ArticleService articleService;
		
	@Test
	void testIndex() {
		// 예상 
		//  모든 게시물을 불러오면 data.sql에 저장했던 데이터들이 불러와 질것이라는 예상
//		Article a = new Article(1L,"한꼬미","천재");
//		Article b = new Article(2L,"두꼬미","처언재");
//		Article c = new Article(3L,"세꼬미","처어언재");
//		
		Article article1 = new Article(1L, "홍길동", "천재");
		Article article2 = new Article(2L, "임꺽정", "처언재");
		Article article3 = new Article(3L, "장길산", "처어언재");
		Article article4 = new Article(4L, "일지매", "처어어언재");
		//Article article5  = new Article(5L,"장꼬미","천천천");
		
		// List만들기 
		List<Article> expected = new ArrayList<Article>(Arrays
				.asList(article1,article2,article3,article4));
		
		// 실제
		// 데이터베이스에서 데이터를 가져오는 서비스를 이용해서 데이터를 저장하고
		// 실제 내용과 위에 들어가는 내용을 비교하는 메서드로 asserEquals()
		List<Article> articles = articleService.index();
		
		// 비교 
		assertEquals(expected.toString(), articles.toString());
		
		
	}
	
	@Test
	void testShow_성공_존재하는_id() {
		// 예상 
		Long id = 1L;
		Article expected = new Article(id,"홍길동","천재");
		// 실제
		Article article = articleService.show(id);
		
		// 비교 
		assertEquals(expected.toString(), article.toString());
	}
	
	@Test
	void testShow_실패_존재하는_않는_id() {
		// 예상 
		Long id = -1L;
		Article expected = null;
		// 실제
		Article article = articleService.show(id);
		
		// 비교 
		assertEquals(expected, article);
	}
	
	// 테이블이 변경되는 테스트들 실행하는 경우 이전 테이터의 영향을 
	// 받아서 하나씩 테스트할 때는 정상적으로 실행 된다.
	// 테스트가 오류가 발생할 수 있기 때문에 테스트 결과가 테이블을 변경시키는 
	// 테스트는 트랜잭션 어노테이션을 이용해서 테스트가 끝나면 롤백할 수있도록
	// 해야한다. 
	
	
	@Test
	@Transactional
	void testCreate_성공_title과_content만_있는_dto입력() {
		String title = "fff";
		String content = "수리수리마수리";
		
		ArticleForm dto = new ArticleForm(null, title,content);
		
		Article expected = new Article(5L,title,content);
		
		Article article = articleService.create(dto);
		
		assertEquals(expected.toString(), article.toString());
	}
	
	
// junit
// 자바를 위한 단위 테스트 라이브러리 
// assert() 테스트 케이스의 수행 결과를 판별해 알려준다.
// assertEquals(A,B) 객체와 A와 B가 같은 값을 가지는지 확인
// assertArryEquals(A,B) 배열 A와 B가 같은지 확인한다.
// assertTrue(A) 조건 A가 참인지 확인한다.
//  assertNull(A) 조건 A가 널인지 확인한다.
	
// @Test
// 테스트 메서드를 지정한다. 

// @Test(timeout=밀리초)
// 테스트 메서드 수행시간을 제한할 수 있다. 
	
// @Test(expected = 예외)
// 해당 테스트 메소드 예외발생 여부에 따라 성공/ 실패를 판별할 수있다.
// expected= 에 지정된 예외가 발생해야 테스트가 성공한 것으로 판별한다. 
	
	

//
//	@Test
//	void testCreate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDelete() {
//		fail("Not yet implemented");
//	}

}
