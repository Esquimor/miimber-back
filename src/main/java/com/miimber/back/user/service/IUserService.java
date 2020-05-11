package com.miimber.back.user.service;

import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.user.model.User;

public interface IUserService extends TemplateService<User> {
	
	List<User> getUsers();
	
	void updatePasswordUser(User user, String newPassword);
	
	void deleteUserById(long id);
	
	User getUserByEmail(String email);
	
	User getUserByEmailAndStatusValidated(String email);
}
