package com.kh.restapi.twoproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Article {
	// 기본키로 사용할 필드 선언
	// -> 자동 생성하려면 애노테이션 명시 (import주의! persistence로)
	@Id	// 필드를 기본키로 설정
	//@GeneratedValue	// 기본키 값을 자동으로 생성 (시퀀스 오토.. 시퀀스가 1부터 시작)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// 테이블 컬럼과 매핑
	@Column
	private String title;
	@Column
	private String content;
	public void patch(Article article) {
		
		if(article.title != null) {
			this.title = article.title;
		}
		
		if(article.content != null) {
			this.content = article.content;
		}
	}
	
}
