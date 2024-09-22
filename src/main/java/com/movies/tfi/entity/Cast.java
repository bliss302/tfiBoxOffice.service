package com.movies.tfi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cast {
    private List<Long> heroIds;
    private List<Long> heroineIds;
    private long directorId;
    private List<Long> otherActors;
}
