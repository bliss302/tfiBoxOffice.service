package com.movies.tfi.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse {
    public List<MovieDto> movies;
    public List<String> regions;
    public List<String > categories;
}
