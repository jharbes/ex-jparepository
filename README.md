<img align="right" alt="Spring" width="100" src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original.svg">

# ex-jparepository

<h3>Exercising Project - JpaRepository/Spring Boot</h3>
<br>

## Trechos de código usados

<h3>Os códigos utilizados são úteis para buscas em banco de dados e consultas refinadas, serão salvos e disponibilizados no README para facilitar futuras utilizações</h3>
<br>

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

```java controller

Classe Controller


@Autowired
private UserRepository repository;



// endpoint para retornar todos os usuarios na mesma pagina
@GetMapping
public ResponseEntity<List<User>> findAll() {
    List<User> result = repository.findAll();
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
    Page<User> result = repository.findAll(pageable);
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
 * localhost:8080/users/search-salary?minSalary=7000&maxSalary=9000%size=5 
 * (idem ao anterior mas com 5 elementos por pagina)
 * 
 * conforme pode ser notado as consultas podem incluir outros parametros para refina-la
 */
@GetMapping(value = "/search-salary")
public ResponseEntity<Page<User>> searchBySalary(@RequestParam(defaultValue = "0") Double minSalary, 
@RequestParam(defaultValue = "1000000000000") Double maxSalary, Pageable pageable) {
    Page<User> result = repository.findBySalaryBetween(minSalary, maxSalary, pageable);
    return ResponseEntity.ok(result);
}




@GetMapping(value = "/search-name")
public ResponseEntity<Page<User>> searchByName(@RequestParam(defaultValue = "") String name, Pageable pageable) {
    Page<User> result = repository.findByNameContainingIgnoreCase(name, pageable);
    return ResponseEntity.ok(result);
}
```


```java repository

Interface Repository


// busca feita por meio de comando no bean @Query para fazer a busca pelo
// salario restringindo faixa de valores
@Query("SELECT obj FROM User obj WHERE obj.salary >= :minSalary AND obj.salary <= :maxSalary")
Page<User> searchBySalary(Double minSalary, Double maxSalary, Pageable pageable);



@Query("SELECT obj FROM User obj WHERE LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))")
Page<User> searchByName(String name, Pageable pageable);



// JPA QUERY METHODS/QUERY CREATION (metodos prontos do Spring JPA que dispensam a criacao de Querys, 
// consultar documentacao do spring)

Page<User> findBySalaryBetween(Double minSalary, Double maxSalary, Pageable pageable);



Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
```