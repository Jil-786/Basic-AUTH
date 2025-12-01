package com.spring.Security.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.Security.entity.UserEntity;

public interface UserRepo extends MongoRepository<UserEntity, String> {

	 UserEntity findByUsername(String username);
	

}
