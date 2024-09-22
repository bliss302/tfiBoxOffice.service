package com.movies.tfi.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto {
    private int pageNo;
    private int size;
}
