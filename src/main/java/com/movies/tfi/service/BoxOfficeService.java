package com.movies.tfi.service;

import com.movies.tfi.payload.BoxOfficeDto;
import com.movies.tfi.payload.GetRecordRequestDto;
import com.movies.tfi.payload.Response;

import java.util.List;

public interface BoxOfficeService {
    public BoxOfficeDto createBoxOffice(BoxOfficeDto boxOfficeDto);
    public List<BoxOfficeDto> getAllBoxOffice(int pageNo, int pageSize, String  sortBy, String sortDir);
    public BoxOfficeDto getBoxOfficeById(long boxOfficeId);
    public Response<List<BoxOfficeDto>> getBoxOffice(GetRecordRequestDto getRecordRequestDto);
    public void deleteBoxOfficeById(long boxOfficeId);
    public BoxOfficeDto updateBoxOfficeById(long boxOfficeId, BoxOfficeDto boxOfficeDto);
    public List<String > getAreas();
    public List<String > getCategories();
}
