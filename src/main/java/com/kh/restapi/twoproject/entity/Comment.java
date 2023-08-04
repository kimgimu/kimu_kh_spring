package com.kh.restapi.twoproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kh.restapi.twoproject.dto.CommentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
public class Comment {

	@Id   // 기본키 
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 기본 시퀀스를 DB가 자동으로 증가시킨다. 
	private Long id;
	
	@Column
	private String nickname;
	
	@Column
	private String body;
	
			   // jpa에 단방향 
	@ManyToOne // 댓글 entity(Comment) 여러개가 하나의 메인글(Article)에 연관된다.
	@JoinColumn(name = "article_id")// article_id컬럼에 article의 대표값(기본키)을 저장한다.
	private Article article;        // 댓글의 부모게시글!
	
	
	// CommentDto를 entity로 변환하는 메서드(댓글,메인글)
	public static Comment createComment(CommentDto dto,Article article) {
		log.info("Comment의 createComment()메서드 실행");
		log.info("dto:{}",dto);
		log.info("Article:{}",article);
		
		// 예외발생
		// 댓글의 id는 DB가 자동으로 붙여주기 때문에 id가 넘어오는 경우 예외를 발생시킨다.
		if(dto.getId() != null) {
			throw new IllegalArgumentException("댓글 생성 실패!!! 댓글의 id는 없어야한다");
			
		}
		// 댓글을 생성하기 위해 요청한 id와 DB에 저장된 id가 다를 경우 예외를 발생시킨다.
		
		if(dto.getArticleId() != article.getId()) {
			throw new IllegalArgumentException("댓글 생성 실패!!! 게시글의 id가 잘못되었습니다!");
			
		}
		
		// entity변환
		return new Comment(dto.getId(),dto.getNickname()
										,dto.getBody(),article);
	}


	// 댓글 수정 하는 메서드 
	public void patch(CommentDto dto) {
		log.info("Comment의 patch()메서드 실행");
		log.info("dto:{}",dto);
		
		// 댓글을 수정하기 위해 요청한 id와 DB에 저장된 id가 다를 경우 예외를 발생시킨다.
		if(this.id != dto.getId()) {
			throw new IllegalArgumentException("댓글 수정 실패!! 잘못된 id가 입력되었습니다.!");
		}
		
		// 댓글 작성자 이름이 넘어왔는가?
		if(dto.getNickname() != null) {
			this.nickname = dto.getNickname();
		}
		
		
		// 수정할 댓글 내용이 넘어왔는가?
		if(dto.getBody() != null) {
			this.body = dto.getBody();
		}	
		
	}
	
}



