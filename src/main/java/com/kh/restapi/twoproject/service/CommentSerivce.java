package com.kh.restapi.twoproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.restapi.twoproject.dto.CommentDto;
import com.kh.restapi.twoproject.entity.Article;
import com.kh.restapi.twoproject.entity.Comment;
import com.kh.restapi.twoproject.repository.ArticleRepository;
import com.kh.restapi.twoproject.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentSerivce {

	// 데이터베이스 접근을 위해서 Repository있어야된다.
	// 게시글, 댓글관리하는 두가지를 객체 생성(bean)
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	public List<CommentDto> comments(Long articleId) {
		log.info("CommentService의 comments() 메소드 실행");
		log.info("articleId: {}", articleId);
		
		// 댓글 목록 조회
		/*
		List<Comment> comments = commentRepository.findByArticleId(articleId);
		log.info("comments: {}", comments);
		
		// entity를 dto로 변환
		List<CommentDto> dtos = new ArrayList<CommentDto>();
//		for (int i=0; i<comments.size(); i++) {
//			Comment comment = comments.get(i);
//			// entity를 dto로 변환하는 메소드를 호출한다.
//			CommentDto dto = CommentDto.createCommentDto(comment);
//			dtos.add(dto);
//		}
		for (Comment comment : comments) {
			CommentDto dto = CommentDto.createCommentDto(comment);
			dtos.add(dto);
		}
		log.info("dtos: {}", dtos);
		
//		entity를 dto로 변환한 결과를 반환한다.
		return dtos;
		*/
		
//		stream
		return commentRepository.findByArticleId(articleId)
				.stream()
				.map(comment -> CommentDto.createCommentDto(comment))
				.collect(Collectors.toList());
	}
	// 댓글 생성
	// 만약 댓글 생성에 실패하면 실행 전 상태로 되돌려야 하므로 트랜잭션 처리를
	// 행야된다.

	@Transactional
	public CommentDto create(Long articleId, CommentDto dto) {

		log.info("CommentSerivce 에서 create()메서드 실행");
		log.info("articleId:{}", articleId);
		log.info("dto:{}", dto);

		// 게시글 조회 및 예외 발생
		// 댓글을 저장하려는 메인글이 있으면 얻어오고 없으면 예외를 발생시킨다.
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!! articleId에  해당되는 메인글이 없습니다.!"));

		// 댓글 엔티티를 생성
		// dto를 엔티티로 변환하는 메서드를 호출한다.
		Comment comment = Comment.createComment(dto, article);

		// 댓글 엔티티를 DB에 저장한다.
		Comment created = commentRepository.save(comment);

		// dto로 변환해서 리턴한다.
		return CommentDto.createCommentDto(created);

	}

	// 댓글 수정을 실행
	public CommentDto update(Long id, CommentDto dto) {
		log.info("CommentSerivce 에서 update()메서드 실행");
		log.info("id:{}", id);
		log.info("dto:{}", dto);

		// 게시글 조회 및 예외 발생
		// 수정할 댓글이 있으면 얻어오고 없으면 예외를 발생시킨다.
		// "댓글 수정 실패 id에 해당되는 댓글이 없습니다.!"
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!! id에 해당되는 댓글이 없습니다!"));

		// 댓글 수정, 댓글을 수정하는 메서드를 호출 한다.
		// dto
		comment.patch(dto);

		// 수정된 댓글로 DB로 갱신한다.
		// save()
		Comment updated = commentRepository.save(comment);

		// 수정한 댓글 entity를 dto로 변환 및 반환
		return CommentDto.createCommentDto(updated);

	}

	// 댓글을 삭제을 실행
	// 메서드명 delete(아이디)
	// 예외처리
	// 댓글 삭제
	// delete()
	// return
	
	public CommentDto delete(Long id) {
		log.info("CommentSerivce 에서 delete()메서드 실행");
		log.info("id:{}", id);
	
		// 게시글 조회 및 예외 발생
		
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException
						("댓글 삭제 실패!! id에 해당되는 댓글이 없습니다!"));

		// 댓글 삭제 
		commentRepository.delete(comment);

		// 삭제한 댓글 entity를 dto반환
		return CommentDto.createCommentDto(comment);

	}

	
	

}
