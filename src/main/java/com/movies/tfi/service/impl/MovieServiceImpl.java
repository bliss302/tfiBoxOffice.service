package com.movies.tfi.service.impl;

import com.movies.tfi.entity.Cast;
import com.movies.tfi.entity.Movie;
import com.movies.tfi.exception.ResourceNotFoundException;
import com.movies.tfi.payload.*;
import com.movies.tfi.repository.MovieRepository;
import com.movies.tfi.service.MovieService;
import com.movies.tfi.utils.CollectionEnums;
import com.movies.tfi.utils.FieldsEnums;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    private String searchQuery = "{'title': { $regex: ?0, $options: 'i' }}";

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        System.out.println("creating movie...");
        Movie movie = mapToEntity(movieDto);
        Movie newMovie = movieRepository.save(movie);
        return mapToDto(newMovie);
    }

    @Override
    public List<MovieDto> getAllMovies(int pageNo, int pageSize, String sortBy, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Movie> movies;
        if(search == null){
            movies = movieRepository.findAll(pageable);
        }else{
            movies = movieRepository.searchMovieUsingTitle(search,pageable);
        }

//        Page<Movie> movies = movieRepository.findAll(pageable);
        List<Movie> movieList = movies.getContent();
        List<MovieDto> movieDtos = movieList.stream().map(movie -> mapToDto(movie)).toList();
        return movieDtos;
    }

    @Override
    public Response<List<MovieDto>> getAllMoviesForMoviePage(int pageNo, int pageSize, String sortBy, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);



        Query searchQuery = getSearchMovieQuery(search);
        long totalRecords = mongoTemplate.count(searchQuery,Movie.class, CollectionEnums.Collections.MOVIES.toName());

        searchQuery.with(pageable);
        List<Movie> movieList = mongoTemplate.find(searchQuery,Movie.class, CollectionEnums.Collections.MOVIES.toName());

        List<MovieDto> movieDtos = movieList.stream().map(element -> mapToDto(element)).toList();
        MetaData metaData = new MetaData();
        metaData.setTotalItems(totalRecords);
        metaData.setHasMore((long) (pageNo + 1) * pageSize < totalRecords);

        Response<List<MovieDto>> response = new Response<>().<List<MovieDto>>builder()
                .data(movieDtos)
                .metaData(metaData)
                .build();
        return response;
//        Page<Movie> movies;
//        if(search == null){
//            movies = movieRepository.findAll(pageable);
//        }else{
//            movies = movieRepository.searchMovieUsingTitle(search,pageable);
//        }
//
////        Page<Movie> movies = movieRepository.findAll(pageable);
//        List<Movie> movieList = movies.getContent();
//        List<MovieDto> movieDtos = movieList.stream().map(movie -> mapToDto(movie)).toList();
//        return null;
    }

    @Override
    public List<MovieDto> getAllMovies(PageDto page, SortDto sort, String search) {

        return getAllMovies(page.getPageNo(), page.getSize(),sort.getSortBy(), sort.getSortDir(),search );
    }

    @Override
    public MovieDto getMovieByMovieId(long movieId) {
        Movie movie = movieRepository.findByMovieId(movieId);
        if(movie==null) {
            throw new ResourceNotFoundException("movie","movieId",movieId);
        }
        return mapToDto(movie);
    }

    @Override
    public void deleteMovieByMovieId(long movieId) {
        movieRepository.deleteByMovieId(movieId);
    }

    @Override
    public MovieDto updateMovieByMovieId(long movieId, MovieDto movieDto) {
        Movie movie = movieRepository.findByMovieId(movieId);
        if(movie==null){
            throw new ResourceNotFoundException("Movie", "movieId",movieId);
        }
//        MovieDto newMovieDto = new MovieDto();
        movie.setMovieId(movieDto.getMovieId() !=0 ? movieDto.getMovieId() : movie.getMovieId());

        movie.setCast(movieDto.getCast() !=null ? mapper.map(movieDto.getCast(), Cast.class) : movie.getCast());
        movie.setGenres(movieDto.getGenres() != null ? movieDto.getGenres() : movie.getGenres());
        movie.setLanguage(movieDto.getLanguage() !=null && !movieDto.getLanguage().isEmpty() ? movieDto.getLanguage() : movie.getLanguage() );
        movie.setTitle(movieDto.getTitle() !=null && !movieDto.getTitle().isEmpty() ? movieDto.getTitle() : movie.getTitle());

//        movie.setShareCollectionId(movieDto.getShareCollectionId() !=0 ? movieDto.getShareCollectionId() : movie.getShareCollectionId());
//        movie.setGrossCollectionId(movieDto.getGrossCollectionId() !=0 ? movieDto.getGrossCollectionId() : movie.getGrossCollectionId());
        movie.setReleaseDate(movieDto.getReleaseDate() !=null ? movieDto.getReleaseDate() : movie.getReleaseDate());

        Movie updatedMovie = movieRepository.save(movie);
        return mapToDto(updatedMovie);
    }

    public List<MovieDto> getMovie(String searchText, String type){
        Query searchQuery = getSearchMovieQuery(searchText);
        Query projectionQuery = getProjectionQuery(type);

        for(String field: projectionQuery.getFieldsObject().keySet()){
            searchQuery.fields().include(field);
        }

        List<Movie> movieList = mongoTemplate.find(searchQuery,Movie.class, CollectionEnums.Collections.MOVIES.toName());

        List<MovieDto> movieDtos = movieList.stream().map(element -> mapToDto(element)).toList();
        return movieDtos;
    }

    public List<MovieDto> getMovie(String searchText, String type,PageDto page, SortDto sortDto){
        int pageNo = page.getPageNo();
        int pageSize = page.getSize();
        String sortBy = sortDto.getSortBy();
        String sortDir = sortDto.getSortDir();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Query searchQuery = getSearchMovieQuery(searchText);
        Query projectionQuery = getProjectionQuery(type);

        for(String field: projectionQuery.getFieldsObject().keySet()){
            searchQuery.fields().include(field);
        }
        searchQuery.with(pageable);
        searchQuery.with(sort);

        List<Movie> movieList = mongoTemplate.find(searchQuery,Movie.class, CollectionEnums.Collections.MOVIES.toName());

        List<MovieDto> movieDtos = movieList.stream().map(element -> mapToDto(element)).toList();
        return movieDtos;
    }



    public MovieDto mapToDto(Movie movie){
        return mapper.map(movie, MovieDto.class);
    }

    public Movie mapToEntity(MovieDto movieDto){
        return mapper.map(movieDto,Movie.class);
    }

    //private

    public Query getSearchMovieQuery(String searchText){
        Query query = new Query();
        if(searchText != null && !searchText.equals("")) {
            query.addCriteria(Criteria.where(FieldsEnums.MovieFields.TITLE.toString()).regex(searchText, "i"));
        }
        return query;
    }

    public Query getProjectionQuery(String type){
        Query query = new Query();

        query.fields().include(FieldsEnums.MovieFields.MOVIE_ID.toString())
                .include(FieldsEnums.MovieFields.TITLE.toString());
        if(type !=null && type.equals("movies")){
            query.fields().include(FieldsEnums.MovieFields.RELEASE_DATE.toString());
        }
        return query;
    }
}
