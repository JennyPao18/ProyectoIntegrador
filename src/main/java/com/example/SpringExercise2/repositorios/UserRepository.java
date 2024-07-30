package com.example.SpringExercise2.repositorios;

import com.example.SpringExercise2.entidades.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
