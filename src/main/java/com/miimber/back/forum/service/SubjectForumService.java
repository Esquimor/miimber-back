package com.miimber.back.forum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.forum.model.SubjectForum;
import com.miimber.back.forum.repository.SubjectForumRepository;

@Service
public class SubjectForumService implements ISubjectForumService {

	@Autowired
	private SubjectForumRepository subjectRepository;
	
	@Override
	public SubjectForum create(SubjectForum entity) {
		return subjectRepository.save(entity);
	}

	@Override
	public SubjectForum update(SubjectForum entity) {
		return subjectRepository.save(entity);
	}

	@Override
	public void delete(SubjectForum entity) {
		subjectRepository.delete(entity);
	}

	@Override
	public SubjectForum get(Long id) {
		Optional<SubjectForum> subject = subjectRepository.findById(id);
		if (subject.isEmpty()) {
			return null;
		}
		return subject.get();
	}

}
