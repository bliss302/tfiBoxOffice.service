package com.movies.tfi.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TMDBIResponse {
    private int total_results;
    private List<MovieResult> results;
}
