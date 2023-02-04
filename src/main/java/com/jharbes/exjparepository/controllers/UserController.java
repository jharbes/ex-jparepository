package com.jharbes.exjparepository.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jharbes.exjparepository.entities.User;
import com.jharbes.exjparepository.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// endpoint para retornar todos os usuarios na mesma pagina
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> result = userRepository.findAll();
		return ResponseEntity.ok(result);
	}

	// endpoint para retornar todos os usuarios, porem com paginacao
	/*
	 * argumentos utilizados: localhost:8080/users/page?page=0&size=12 podemos
	 * colocar o numero da pagina (a partir de 0) e o tamanho/numero elementos da
	 * pagina podemos usar apenas pagina também (numero padrao de elementos = 20)
	 * 
	 * localhost:8080/users/page?page=0&size=12&sort=name Ordenando o page por nome
	 * de acordo com todos os parametros repassados
	 * 
	 * localhost:8080/users/page?page=0&size=12&sort=name,asc (ordem crescente)
	 * localhost:8080/users/page?page=0&size=12&sort=name,desc (ordem decrescente)
	 * 
	 * localhost:8080/users/page?page=0&size=12&sort=name,salary,asc (ordena por
	 * nome, depois por salary em ordem crescente)
	 */
	@GetMapping(value = "/page")
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> result = userRepository.findAll(pageable);
		return ResponseEntity.ok(result);
	}
}
