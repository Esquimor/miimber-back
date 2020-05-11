package com.miimber.back.session.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.session.model.CommentSession;
import com.miimber.back.session.repository.CommentSessionRepository;

@Service
public class CommentSessionService implements ICommentSessionService {
	
	@Autowired
	private CommentSessionRepository commentRepository;

	@Override
	public CommentSession create(CommentSession entity) {
		return commentRepository.save(entity);
	}

	@Override
	public CommentSession update(CommentSession entity) {
		return commentRepository.save(entity);
	}

	@Override
	public void delete(CommentSession entity) {
		commentRepository.delete(entity);
	}

	@Override
	public CommentSession get(Long id) {
		Optional<CommentSession> comment = commentRepository.findById(id);
		if (comment.isEmpty()) {
			return null;
		}
		return comment.get();
	}

}
