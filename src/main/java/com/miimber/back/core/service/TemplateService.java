package com.miimber.back.core.service;

public interface TemplateService<T> {

	T create(T entity);
	
	T update(T entity);
	
	void delete(T entity);
	
	T get(Long id);
}
