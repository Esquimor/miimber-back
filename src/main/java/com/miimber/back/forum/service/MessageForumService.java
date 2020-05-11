package com.miimber.back.forum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.forum.model.MessageForum;
import com.miimber.back.forum.repository.MessageForumRepository;

@Service
public class MessageForumService implements IMessageForumService {

	@Autowired
	private MessageForumRepository messageRepository;
	
	@Override
	public MessageForum create(MessageForum entity) {
		return messageRepository.save(entity);
	}

	@Override
	public MessageForum update(MessageForum entity) {
		return messageRepository.save(entity);
	}

	@Override
	public void delete(MessageForum entity) {
		messageRepository.delete(entity);
	}

	@Override
	public MessageForum get(Long id) {
		Optional<MessageForum> message = messageRepository.findById(id);
		if (message.isEmpty()) {
			return null;
		}
		return message.get();
	}

}
