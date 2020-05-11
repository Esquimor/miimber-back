package com.miimber.back.forum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.forum.model.CategoryForum;
import com.miimber.back.forum.repository.CategoryForumRepository;

@Service
public class CategoryForumService implements ICategoryForumService {

	@Autowired
	private CategoryForumRepository categoryRepository;
	
	@Override
	public CategoryForum create(CategoryForum entity) {
		return categoryRepository.save(entity);
	}

	@Override
	public CategoryForum update(CategoryForum entity) {
		return categoryRepository.save(entity);
	}

	@Override
	public void delete(CategoryForum entity) {
		categoryRepository.delete(entity);
	}

	@Override
	public CategoryForum get(Long id) {
		Optional<CategoryForum> category = categoryRepository.findById(id);
		if (category.isEmpty()) {
			return null;
		}
		return category.get();
	}

	@Override
	public List<CategoryForum> getByOrganization(Long id) {
		return categoryRepository.findByOrganizationId(id);
	}

}
