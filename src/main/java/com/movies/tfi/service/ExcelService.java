package com.movies.tfi.service;

import com.movies.tfi.payload.ActorDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelService {
    public List<ActorDto> saveActorsFromExcel(String file);
}
