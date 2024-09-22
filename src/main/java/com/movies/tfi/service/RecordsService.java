package com.movies.tfi.service;

import com.movies.tfi.payload.MovieDto;
import com.movies.tfi.payload.PageDto;
import com.movies.tfi.payload.SearchResponse;
import com.movies.tfi.payload.SortDto;

import java.util.List;

public interface RecordsService {
    public SearchResponse getHomeRecords(PageDto page, SortDto sort, String searchString, String type);
    public List<MovieDto> getMovies(String searchString);
    public PageDto getValueOrDefaultPage(PageDto page, PageDto defaultPage);

    public SortDto getValueOrDefaultSort(SortDto sort, SortDto defaultSort);
}
