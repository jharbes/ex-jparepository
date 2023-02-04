package com.jharbes.exjparepository.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	/*
	 * @RequestParam seta o argumento minimo salario, que caso nao seja informado
	 * sera utilizado o valor 0 idem para o maximo que caso nao seja informado sera
	 * setado o maximo para 1000000000000
	 * 
	 * localhost:8080/users/search-salary?minSalary=10000 faz a busca procurando
	 * quem ganha no minimo 10.000
	 * 
	 * localhost:8080/users/search-salary?minSalary=7000&maxSalary=9000 faz a busca
	 * procurando quem ganha no minimo 7000 e no maximo 9000
	 * 
	 * localhost:8080/users/search-salary?minSalary=7000&maxSalary=9000%size=5 (idem
	 * ao anterior mas com 5 elementos por pagina)
	 * 
	 * conforme pode ser notado as consultas podem incluir outros parametros para
	 * refina-la
	 */
	@GetMapping(value = "/search-salary")
	public ResponseEntity<Page<User>> searchBySalary(@RequestParam(defaultValue = "0") Double minSalary,
			@RequestParam(defaultValue = "1000000000000") Double maxSalary, Pageable pageable) {
		Page<User> result = userRepository.searchBySalary(minSalary, maxSalary, pageable);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/search-name")
	public ResponseEntity<Page<User>> searchByName(@RequestParam(defaultValue = "") String name, Pageable pageable) {
		Page<User> result = userRepository.searchName(name, pageable);
		return ResponseEntity.ok(result);
	}

	// implementacao do metodo pre pronto do spring jpa que faz o mesmo que o metodo
	// search by salary acima
	@GetMapping(value = "/find-by-salary")
	public ResponseEntity<Page<User>> findBySalaryBetween(@RequestParam(defaultValue = "0") Double minSalary,
			@RequestParam(defaultValue = "1000000000000") Double maxSalary, Pageable pageable) {
		Page<User> result = userRepository.findBySalaryBetween(minSalary, maxSalary, pageable);
		return ResponseEntity.ok(result);
	}
}
