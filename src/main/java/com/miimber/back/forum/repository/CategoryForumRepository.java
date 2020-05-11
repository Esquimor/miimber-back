package com.miimber.back.forum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.forum.model.CategoryForum;

public interface CategoryForumRepository extends CrudRepository<CategoryForum, Long>{

	List<CategoryForum> findByOrganizationId(Long id);
}
