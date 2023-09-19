package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.User;

public interface UserRespority extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
	public User findByVarificationcode(String code);

}
