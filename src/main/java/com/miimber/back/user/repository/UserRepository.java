package com.miimber.back.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.user.model.User;
import com.miimber.back.user.model.enums.StatusEnum;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	User findByEmailAndStatus(String email, StatusEnum status);
}
