package com.movies.tfi.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoxOfficeDto {
    private long boxOfficeId;
    private long movieId;
//    private String type;

    private RegionDto breakEven;
    private RegionDto ap;  //rupee
    private RegionDto nizam;
    private RegionDto vizag;
    private RegionDto east;
    private RegionDto west;
    private RegionDto krishna;
    private RegionDto guntur;
    private RegionDto nellore;
    private RegionDto ceded;
    private RegionDto karnataka;
    private RegionDto tamilNadu;
    private RegionDto kerala;
    private RegionDto hindiBelt;
    private RegionDto Overseas;
    private RegionDto WorldWideTheatrical;
}
