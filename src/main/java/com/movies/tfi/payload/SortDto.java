package com.movies.tfi.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortDto {
    private String sortBy;
    private String sortDir;
}
