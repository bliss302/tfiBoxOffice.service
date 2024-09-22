package com.movies.tfi.service.impl;

import com.movies.tfi.payload.MovieDto;
import com.movies.tfi.payload.PageDto;
import com.movies.tfi.payload.SearchResponse;
import com.movies.tfi.payload.SortDto;
import com.movies.tfi.repository.MovieRepository;
import com.movies.tfi.service.BoxOfficeService;
import com.movies.tfi.service.MovieService;
import com.movies.tfi.service.RecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordsServiceImpl implements RecordsService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    BoxOfficeService boxOfficeService;

    @Autowired
    MovieService movieService;

    @Override
    public SearchResponse getHomeRecords(PageDto page, SortDto sort, String searchString, String type) {
        List<String > regions = boxOfficeService.getAreas();
        List<MovieDto> movieDtoList = movieService.getMovie(searchString,type,page,sort);
        List<String> categories = boxOfficeService.getCategories();

        return new SearchResponse(movieDtoList,regions,categories);
    }

    @Override
    public List<MovieDto> getMovies(String searchString) {
        return null;
    }

    @Override
    public PageDto getValueOrDefaultPage(PageDto pageDto,PageDto defaultPage){
        if(pageDto == null){
            return defaultPage;
        } else if (pageDto.getPageNo() == 0) {
            pageDto.setPageNo(defaultPage.getPageNo());
        }else if(pageDto.getSize() ==0){
            pageDto.setSize(defaultPage.getSize());
        }
        return pageDto;
    }

    @Override
    public SortDto getValueOrDefaultSort(SortDto sort, SortDto defaultSort){
        if(sort == null){
            return defaultSort;
        } else if (sort.getSortBy() == null || sort.getSortBy().equals("")) {
            sort.setSortBy(defaultSort.getSortBy());
        }else if(sort.getSortDir() == null || sort.getSortBy().equals("")){
            sort.setSortDir(defaultSort.getSortDir());
        }
        return sort;
    }
}
