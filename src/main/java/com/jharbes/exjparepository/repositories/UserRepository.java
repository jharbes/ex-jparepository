package com.jharbes.exjparepository.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jharbes.exjparepository.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// busca feita por meio de comando no bean @Query para fazer a busca pelo
	// salario restringindo faixa de valores
	@Query("SELECT obj FROM User obj WHERE obj.salary >= :minSalary AND obj.salary <= :maxSalary")
	Page<User> findBySalaryBetween(Double minSalary, Double maxSalary, Pageable pageable);

}
