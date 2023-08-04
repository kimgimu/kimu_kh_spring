package com.kh.restapi.twoproject.dto;

import com.kh.restapi.twoproject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Data
public class ArticleForm {
	
	private Long id;
	private String title;
	private String content;
	
	public Article toEntity() {
		return new Article(id,title,content);
	}
	
}
