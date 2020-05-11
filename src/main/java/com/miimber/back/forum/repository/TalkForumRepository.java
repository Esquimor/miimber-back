package com.miimber.back.forum.repository;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.forum.model.TalkForum;

public interface TalkForumRepository extends CrudRepository<TalkForum, Long> {

}
