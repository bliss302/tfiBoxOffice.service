package com.movies.tfi.repository;

import com.movies.tfi.entity.Actor;
import com.movies.tfi.entity.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, Long> {
    public Movie findByMovieId(long movieId);
    public void deleteByMovieId(long movieId);

    @Query("{'title': { $regex: ?0, $options: 'i' }}")
    List<Movie> searchMovieUsingTitle(String text);

    @Query("{'title': { $regex: ?0, $options: 'i' }}")
    Page<Movie> searchMovieUsingTitle(String text, Pageable pageable);
}
