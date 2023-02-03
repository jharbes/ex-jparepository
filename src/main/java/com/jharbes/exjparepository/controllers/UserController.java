package com.jharbes.exjparepository.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jharbes.exjparepository.entities.User;
import com.jharbes.exjparepository.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<List<User>> findAll() {
		List<User> result = userRepository.findAll();
		return ResponseEntity.ok(result);
	}
}
