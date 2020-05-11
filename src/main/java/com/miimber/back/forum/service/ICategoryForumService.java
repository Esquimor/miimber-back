package com.miimber.back.forum.service;

import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.forum.model.CategoryForum;

public interface ICategoryForumService extends TemplateService<CategoryForum>{

	List<CategoryForum> getByOrganization(Long id);
}
