package com.movies.tfi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "boxOffice")
public class BoxOffice {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private long boxOfficeId;
    private long movieId;
//    private String type;
//    private List<areaRevenu> areas;


    private Region breakEven;
    private Region ap;  //rupee
    private Region nizam;
    private Region vizag;
    private Region east;
    private Region west;
    private Region krishna;
    private Region guntur;
    private Region nellore;
    private Region ceded;
    private Region karnataka;
    private Region tamilNadu;
    private Region kerala;
    private Region hindiBelt;
    private Region Overseas;
    private Region WorldWideTheatrical;


}
