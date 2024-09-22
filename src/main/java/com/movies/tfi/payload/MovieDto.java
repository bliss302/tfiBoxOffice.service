package com.movies.tfi.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.movies.tfi.serializer.DateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MovieDto {
    private long movieId;
    private String title;
    private String language;
    private List<String> genres;

    @JsonDeserialize(using = DateDeserializer.class)
    private Date releaseDate;
    private long boxOfficeId;
//    private long shareCollectionId;
//    private long grossCollectionId;
    private CastDto cast;
}
