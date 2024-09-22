package com.movies.tfi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.movies.tfi.serializer.DateSerializer;
import jakarta.annotation.Generated;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "actors")
public class Actor {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private long actorId;
    private String firstName;
    private String lastName;

    @JsonSerialize(using = DateSerializer.class)
    private Date dateOfBirth;
    private String category;
}
