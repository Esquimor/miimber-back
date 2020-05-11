package com.miimber.back.session.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.session.model.TemplateSession;
import com.miimber.back.session.repository.TemplateSessionRepository;

@Service
public class TemplateSessionService implements ITemplateSessionService {

	@Autowired
	private TemplateSessionRepository templateSessionRepository;
	
	@Override
	public TemplateSession create(TemplateSession entity) {
		return templateSessionRepository.save(entity);
	}

	@Override
	public TemplateSession update(TemplateSession entity) {
		return templateSessionRepository.save(entity);
	}

	@Override
	public void delete(TemplateSession entity) {
		templateSessionRepository.delete(entity);
	}

	@Override
	public TemplateSession get(Long id) {
		Optional<TemplateSession> templateSession = templateSessionRepository.findById(id);
		if (templateSession.isEmpty())	{
			return null;
		}
		return templateSession.get();
	}

}
