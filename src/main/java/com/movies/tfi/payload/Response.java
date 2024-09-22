package com.movies.tfi.payload;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Response<T> {
    private String  message;
    private int statusCode;
    private T data;
    private MetaData metaData;
}
