package com.movies.tfi.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.movies.tfi.serializer.DateDeserializer;
import com.movies.tfi.serializer.ObjectIdToStringDeSerializer;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorDto {

//    @JsonDeserialize(using = ObjectIdToStringDeSerializer.class)
    private long actorId;
    private String firstName;
    private String lastName;

    private String category;

    @JsonDeserialize(using = DateDeserializer.class)
    private Date dateOfBirth;
}
