package com.movies.tfi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRecordRequestDto {
    private long movieId;
    private List<String > regions;
    private String category;
    private PageDto page;
    private SortDto sort;
}
