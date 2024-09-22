package com.movies.tfi.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    public void saveActorsFromExcel(String file);
}
