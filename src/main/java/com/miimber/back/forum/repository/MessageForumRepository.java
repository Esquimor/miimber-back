package com.miimber.back.forum.repository;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.forum.model.MessageForum;

public interface MessageForumRepository extends CrudRepository<MessageForum, Long>{

}
