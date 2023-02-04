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
	Page<User> searchBySalary(Double minSalary, Double maxSalary, Pageable pageable);

	// o comando LOWER na query se trata de lowercase(passar todo para minuscula), e
	// like lower(CONCAT('%':name,'%')) faz a consulta passando como se o nome
	// procurado pudesse estar inserido no meio de qualquer string de nome, ou seja
	// estivesse contida na string do nome
	@Query("SELECT obj from User obj WHERE LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))")
	Page<User> searchName(String name, Pageable pageable);

	// esse é um dos métodos ja prontos do spring jpa que fazem a busca mas ja
	// possuem o query pronto, sendo desnecesario a sua construcao, nesse caso ele
	// funciona exatamente como o outro metodo searchBySalary acima
	Page<User> findBySalaryBetween(Double minSalary, Double maxSalary, Pageable pageable);
	
	// idem ao anterior trata-se um metodo pronto do jpa spring (jpa.query-methods), so que agora para o nome
	Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
