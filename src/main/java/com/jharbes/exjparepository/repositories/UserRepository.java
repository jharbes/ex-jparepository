package com.jharbes.exjparepository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jharbes.exjparepository.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
