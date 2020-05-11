package com.miimber.back.session.repository;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.session.model.CommentSession;

public interface CommentSessionRepository  extends CrudRepository<CommentSession, Long> {

}
