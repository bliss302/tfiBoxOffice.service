package com.movies.tfi.service;

import com.movies.tfi.payload.MovieDto;
import com.movies.tfi.payload.PageDto;
import com.movies.tfi.payload.SortDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    public MovieDto createMovie(MovieDto movieDto);
    public List<MovieDto> getAllMovies(int pageNo, int pageSize, String sortBy, String SortDir, String search);
    public List<MovieDto> getAllMovies(PageDto page, SortDto sortDto, String search);
    public MovieDto getMovieByMovieId(long movieId);
    public List<MovieDto> getMovie(String searchText, String type);
    public List<MovieDto> getMovie(String searchText, String type,PageDto pageDto, SortDto sortDto);
    public void deleteMovieByMovieId(long movieId);
    public MovieDto updateMovieByMovieId(long movieId, MovieDto movieDto);


}
