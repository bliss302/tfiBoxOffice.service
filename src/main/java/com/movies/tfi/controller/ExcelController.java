package com.movies.tfi.controller;

import com.movies.tfi.payload.ActorDto;
import com.movies.tfi.payload.MetaData;
import com.movies.tfi.payload.Response;
import com.movies.tfi.payload.UploadDto;
import com.movies.tfi.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
