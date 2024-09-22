package com.movies.tfi.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaData {
    private Long totalItems;
    private Boolean hasMore;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
}
