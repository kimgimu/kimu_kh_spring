package com.kh.restapi.twoproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kh.restapi.twoproject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

	
	// @Query 어노테이션으로 query를 만들어 사용할 수 있다. 
	// 특정 게시글의 모든 댓글을 조회하는 메서드 
	@Query(value = "select * from comment where article_id = :articleId"
			, nativeQuery = true)
	List<Comment> findByArticleId(Long articleId);
	
	// 특정 닉네임의 모든 댓글을 조회하는 메서드 
	// ORM은 객체와 관계형 데이베이스의 데이터를 자동으로 매핑(연결)해주는 것을 말한다.
	
	List<Comment> findByNickname(String nickname);
	
	
	
	
	
}
