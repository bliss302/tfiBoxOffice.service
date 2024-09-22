package com.movies.tfi.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.movies.tfi.serializer.DateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private long movieId;

    private String title;
    private String language;
    private List<String> genres;

    @JsonSerialize(using = DateSerializer.class)
    private Date releaseDate;
    private long boxOfficeId;
//    private long shareCollectionId;
//    private long grossCollectionId;
    private Cast cast;
}
