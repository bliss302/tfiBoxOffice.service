package com.movies.tfi.controller;

import com.movies.tfi.payload.*;
import com.movies.tfi.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tfi")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @PostMapping("/uploadActors")
    public ResponseEntity<Response<List<ActorDto>>> uploadActors(@RequestBody UploadDto uploadDto){
        List< ActorDto> actorDtos = excelService.saveActorsFromExcel(uploadDto.getFileName());
        MetaData metaData = MetaData.builder()
                .totalItems((long) actorDtos.size())
                .build();
        Response response = Response.<List<ActorDto>>builder()
                .data(actorDtos)
                .metaData(metaData)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/uploadMovies")
    public ResponseEntity<Response<List<MovieDto>>> uploadMovies(@RequestBody UploadDto uploadDto){
        List<MovieDto> movieDtoList = excelService.saveMoviesFromExcel(uploadDto.getFileName());
        MetaData metaData = MetaData.builder()
                .totalItems((long) movieDtoList.size())
                .build();
        Response response = Response.builder()
                .data(movieDtoList)
                .metaData(metaData)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/uploadBoxOffice")
    public ResponseEntity<Response<List<BoxOfficeDto>>> uploadBoxOffice(@RequestBody UploadDto uploadDto){
        List<BoxOfficeDto> boxOfficeDtos = excelService.saveBoxOfficeFromExcel(uploadDto.getFileName());
        MetaData metaData = MetaData.builder()
                .totalItems((long) boxOfficeDtos.size())
                .build();
        Response response = Response.builder()
                .data(boxOfficeDtos)
                .metaData(metaData)
                .build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
