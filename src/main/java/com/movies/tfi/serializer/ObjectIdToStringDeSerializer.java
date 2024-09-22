package com.movies.tfi.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdToStringDeSerializer extends JsonDeserializer<String> {


    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String objectId = parser.getText();
        if(ObjectId.isValid(objectId)){
            String value = new ObjectId(objectId).toHexString();
            System.out.println("********************************");
            System.out.println("ObjectId hex String: "+ value);
            System.out.println("********************************");
            return value;
        }

        return null;
    }
}
