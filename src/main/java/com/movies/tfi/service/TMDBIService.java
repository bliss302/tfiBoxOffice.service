package com.movies.tfi.service;

import com.movies.tfi.payload.MovieResult;

public interface TMDBIService {
    public MovieResult getMoviePoster(String movieName);
}
