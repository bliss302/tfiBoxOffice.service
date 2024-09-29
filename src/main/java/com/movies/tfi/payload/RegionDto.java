package com.movies.tfi.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegionDto {
    private BigDecimal share;
    private BigDecimal gross;
    private BigDecimal day1;
}
