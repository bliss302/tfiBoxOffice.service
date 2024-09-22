package com.movies.tfi.repository;

import com.movies.tfi.entity.Actor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ActorRepository extends MongoRepository<Actor, Long> {
    Actor findByActorId(long actorId);
    void deleteByActorId(long actorId);

    //@Query("{ 'firstName': { $regex: ?0, $options: 'i' } }")
    @Query("{ $or:[{'firstName': { $regex: ?0, $options: 'i' }}, {'lastName': { $regex: ?0, $options: 'i' }}]}")
    List<Actor> searchActor(String text);

    @Query("{ $or:[{'firstName': { $regex: ?0, $options: 'i' }}, {'lastName': { $regex: ?0, $options: 'i' }}]}")
    Page<Actor> searchActor(String text, Pageable pageable);
}
