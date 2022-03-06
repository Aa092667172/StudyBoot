package com.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.domain.User;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String>{
	/**
	 * 根據年齡查找用戶
	 * @param strart
	 * @param end
	 * @return
	 */
	Flux<User> findByAgeBetween(int strart,int end);
	
	//＄gte 為>=  $lte 為<=
	@Query("{'age':{'$gte':20,'$lte':30}}")
	Flux<User> oldUser();
	
}
