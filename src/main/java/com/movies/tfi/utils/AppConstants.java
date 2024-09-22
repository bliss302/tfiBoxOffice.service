package com.movies.tfi.utils;

import com.movies.tfi.payload.PageDto;
import com.movies.tfi.payload.SortDto;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final PageDto DEFAULT_PAGE = new PageDto(Integer.parseInt(DEFAULT_PAGE_NUMBER),
            Integer.parseInt(DEFAULT_PAGE_SIZE));
    public static final String DEFAULT_SORT_BY_ACTOR = "actorId";
    public static final String DEFAULT_SORT_BY_BOX_OFFICE = "boxOfficeId";

    public static final String DEFAULT_SORT_DIR = "asc";
    public static final SortDto DEFAULT_SORT = new SortDto(DEFAULT_SORT_BY_ACTOR, DEFAULT_SORT_DIR);
    public static final String RECORDS_MOVIE_SORT_BY = "releaseDate";
}
