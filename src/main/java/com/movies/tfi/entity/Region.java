package com.movies.tfi.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Region {
    private BigDecimal share;
    private BigDecimal gross;
    private BigDecimal day1;

}
