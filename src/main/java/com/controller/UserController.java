package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.User;
import com.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserRepository repository;

	public UserController(UserRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/")
	public Flux<User> getAll() {
		return repository.findAll();
	}
	/**
	 * 以SSE形式多次返回數據
	 * @return
	 */
	@GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<User> streamGetAll() {
		return repository.findAll();
	}
	
	@PostMapping("/")
	public Mono<User> createUser(@RequestBody User user){
		//spring data jpa 內 ,新增和修改都是save 有id 則為修改 id空為新增
		//根據實際情況是否置空id
//		user.setId(null);
		return this.repository.save(user);
	}
	
	/**
	 * 根據id刪除用戶
	 * 存在返回200 不存在返回404
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id){
		//deletebyID 沒有返回值 ,不能判斷數據是否存在
		//his.repository.deleteById(id)
		return this.repository.findById(id)
		//當你要操作數據,並返回一個Mono這時候使用FlatMap
		//如果不操作數據,只是轉換數據,使用map
		.flatMap(user -> this.repository.delete(user)
				.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	@PutMapping("/{id}")
	public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id ,@RequestBody User user){
		return this.repository.findById(id)
		//flatMap操作數據
		.flatMap(u ->{
			u.setAge(user.getAge());
			u.setName(user.getName());
			return this.repository.save(u);
		}).map(u->new ResponseEntity<User>(u,HttpStatus.OK))
		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	/**
	 * 根據id查找用戶
	 * 存在返回數據,不存在返回404
	 * @param id
	 * @param user
	 * @return
	 */
	@GetMapping("/{id}")
	public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") String id ){
		return this.repository.findById(id)
				.map(u-> new ResponseEntity<User>(u,HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
}
