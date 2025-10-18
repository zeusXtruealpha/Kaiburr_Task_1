package com.kaiburr.demo.repository;

import com.kaiburr.demo.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    
    @Query("{ 'name': { $regex: '?0', $options: 'i' } }")
    List<Task> findByNameContaining(String name);
    
    Optional<Task> findById(String id);
}
