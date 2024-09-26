package com.movies.tfi.controller;

import com.movies.tfi.payload.*;
import com.movies.tfi.service.BoxOfficeService;
import com.movies.tfi.service.MovieService;
import com.movies.tfi.service.RecordsService;
import com.movies.tfi.utils.AppConstants;
import com.movies.tfi.utils.RecordsEnums.SearchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.movies.tfi.utils.AppConstants.*;

@RestController
@RequestMapping("tfi/records")
public class RecordsController {
    @Autowired
    MovieService movieService;

    @Autowired
    BoxOfficeService boxOfficeService;

    @Autowired
    RecordsService recordsService;

    @CrossOrigin(origins = "http://localhost:1234")
    @PostMapping()
    public ResponseEntity<Response<SearchResponse>> searchData(@RequestBody SearchDto searchDto){

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

        if(page == null){
            page = PageDto.builder()
                    .pageNo(Integer.parseInt(AppConstants.DEFAULT_PAGE_NUMBER))
                    .size(Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE))
                    .build();
        }
        List<MovieDto> movieDtoList = null;

        Response<SearchResponse> response = new Response<>();
        SearchResponse searchResponse = null;
        List<String > regions =null;
        List<String > categories= null;
        if(type.equals(SearchType.HOME_RECORDS.getType())){
            searchResponse = recordsService.getHomeRecords(page,sort,searchDto.getSearchStr(),type);

        } else if(type.equals(SearchType.MOVIE.getType())) {
            movieDtoList = movieService.getMovie(searchDto.getSearchStr(),type, page, sort );
//            movieDtoList = movieService.getAllMovies(page,sort,searchDto.getSearchStr());
            searchResponse = SearchResponse.builder()
                    .movies(movieDtoList)
                    .build();
        }
        response.setData(searchResponse);
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @PostMapping("/boxOffice")
    public ResponseEntity<Response<List<BoxOfficeDto>>> getBoxOfficeData(@RequestBody GetRecordRequestDto getRecordRequestDto){
        PageDto page = recordsService.getValueOrDefaultPage(getRecordRequestDto.getPage(), DEFAULT_PAGE);
        SortDto sort = recordsService.getValueOrDefaultSort(getRecordRequestDto.getSort(), new SortDto(DEFAULT_SORT_BY_BOX_OFFICE, DEFAULT_SORT_DIR));
        getRecordRequestDto.setPage(page);
        getRecordRequestDto.setSort(sort);
        Response<List<BoxOfficeDto>> response = boxOfficeService.getBoxOffice(getRecordRequestDto);
        return ResponseEntity.ok(response);
    }
}
