package com.example.backuprestore.repository;

import com.example.backuprestore.model.UserWorkout;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkoutRepository extends MongoRepository<UserWorkout, ObjectId> {
}