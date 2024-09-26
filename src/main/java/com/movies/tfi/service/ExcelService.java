package com.movies.tfi.service;

import com.movies.tfi.payload.ActorDto;
import com.movies.tfi.payload.BoxOfficeDto;
import com.movies.tfi.payload.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelService {
    public List<ActorDto> saveActorsFromExcel(String file);
    public List<MovieDto> saveMoviesFromExcel(String file);
    public List<BoxOfficeDto> saveBoxOfficeFromExcel(String file);
}
