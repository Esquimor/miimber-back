package com.miimber.back.session.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.repository.TypeSessionRepository;

@Service
public class TypeSessionService implements ITypeSessionService {

	@Autowired
	private TypeSessionRepository typeSessionRepository;

	@Override
	public List<TypeSession> getTypeSessionByOrganizationId(long id) {
		return typeSessionRepository.findByOrganizationId(id);
	}

	@Override
	public TypeSession create(TypeSession entity) {
		return typeSessionRepository.save(entity);
	}

	@Override
	public TypeSession update(TypeSession entity) {
		return typeSessionRepository.save(entity);
	}

	@Override
	public void delete(TypeSession entity) {
		typeSessionRepository.delete(entity);
	}

	@Override
	public TypeSession get(Long id) {
		Optional<TypeSession> typeSession = typeSessionRepository.findById(id);
		if (typeSession.isEmpty()) {
			return null;
		}
		return typeSession.get();
	}

}
