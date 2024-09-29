package com.movies.tfi.controller;

import com.movies.tfi.payload.*;
import com.movies.tfi.service.MovieService;
import com.movies.tfi.utils.AppConstants;
import com.movies.tfi.utils.RecordsEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tfi/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping()
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto){
        MovieDto movieDto1 = movieService.createMovie(movieDto);
        return ResponseEntity.ok(movieDto1);
    }

    @GetMapping()
    public List<MovieDto> getAllMovies(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY_ACTOR, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir,
            @RequestParam(value ="search", required = false) String search
    ){

        System.out.println("getting all movies...");
        List<MovieDto> movieDtos = movieService.getAllMovies(pageNo,pageSize,sortBy,sortDir,search);
        return movieDtos;
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @PostMapping("/search")
    public ResponseEntity<Response<List<MovieDto>>> getAllMoviesForMoviesPage(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY_ACTOR, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir,
            @RequestBody SearchDto searchDto
    ){
        String type = searchDto.getType();
        SortDto sort = searchDto.getSort();
        PageDto page = searchDto.getPage();
        if(sort == null){
            sort = SortDto.builder()
                    .sortBy(AppConstants.RECORDS_MOVIE_SORT_BY)
                    .sortDir(AppConstants.DEFAULT_SORT_DIR)
                    .build();
        }
        else if(sort.getSortBy() == null)
        {
            sort.setSortBy(AppConstants.RECORDS_MOVIE_SORT_BY);
        }
        else if(sort.getSortDir() == null)
        {
            sort.setSortDir(AppConstants.DEFAULT_SORT_DIR);
        }
        String searchStr = searchDto.getSearchStr();

        if(page == null){
            page = PageDto.builder()
                    .pageNo(Integer.parseInt(AppConstants.DEFAULT_PAGE_NUMBER))
                    .size(Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE))
                    .build();
        }
        System.out.println("getting all movies...");
        Response<List<MovieDto>> response = movieService.getAllMoviesForMoviePage(pageNo,pageSize,sortBy,sortDir,searchStr);
//        boolean hasMore =(long) (pageNo + 1) * pageSize < movieDtos.size();
//        MetaData metaData = MetaData.builder()
//                .totalItems((long)movieDtos.size())
//                .hasMore(hasMore)
//                .build();
//        Response<List<MovieDto>> response = new Response<>().<List<MovieDto>>builder()
//                .data(movieDtos)
//                .metaData(metaData)
//                .build();
        response.setMessage(String.format("fetched data for %s successfully","movies page"));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{movieId}")
    public MovieDto getMovieById(@PathVariable long movieId){
        System.out.printf("getting movie with Id:" + movieId);
        MovieDto movieDto = movieService.getMovieByMovieId(movieId);
        return movieDto;
    }

    @DeleteMapping("/{movieId}")
    public String deleteMovieById(@PathVariable long movieId){
        System.out.println("deleting movie with Id:" + movieId);
        movieService.deleteMovieByMovieId(movieId);
        return String.format("delete movie by Id: %d is Succesfull",movieId);

    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDto> updateMovieById(@PathVariable long movieId, @RequestBody MovieDto movieDto){
        MovieDto movieDto1 = movieService.updateMovieByMovieId(movieId,movieDto);
        return ResponseEntity.ok(movieDto1);
    }
}
