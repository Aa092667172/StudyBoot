package com.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.domain.User;
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String>{

}
