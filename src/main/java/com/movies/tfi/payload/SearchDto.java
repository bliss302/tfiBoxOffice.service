package com.movies.tfi.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto {
    private String searchStr;
    private String type;
    private PageDto page;
    private SortDto sort;

}
