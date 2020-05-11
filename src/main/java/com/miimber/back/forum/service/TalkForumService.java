package com.miimber.back.forum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.forum.model.TalkForum;
import com.miimber.back.forum.repository.TalkForumRepository;

@Service
public class TalkForumService implements ITalkForumService {

	@Autowired
	private TalkForumRepository postRepository;
	
	@Override
	public TalkForum create(TalkForum entity) {
		return postRepository.save(entity);
	}

	@Override
	public TalkForum update(TalkForum entity) {
		return postRepository.save(entity);
	}

	@Override
	public void delete(TalkForum entity) {
		postRepository.delete(entity);
	}

	@Override
	public TalkForum get(Long id) {
		Optional<TalkForum> post = postRepository.findById(id);
		if (post.isEmpty())	 {
			return null;
		}
		return post.get();
	}

}
