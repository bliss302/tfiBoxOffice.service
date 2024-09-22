package com.movies.tfi.controller;

import com.movies.tfi.payload.BoxOfficeDto;
import com.movies.tfi.service.BoxOfficeService;
import com.movies.tfi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tfi/boxOffice")
public class BoxOfficeController {

    @Autowired
    BoxOfficeService boxOfficeService;

    @PostMapping()
    public BoxOfficeDto createBoxOffice(@RequestBody BoxOfficeDto boxOfficeDto){
        BoxOfficeDto boxOfficeDto1 = boxOfficeService.createBoxOffice(boxOfficeDto);
        return boxOfficeDto1;
    }

    @GetMapping()
    public List<BoxOfficeDto> getAllBoxOffice(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_ACTOR, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return boxOfficeService.getAllBoxOffice(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{boxOfficeId}")
    public BoxOfficeDto getBoxOfficeById(@PathVariable long boxOfficeId){
        BoxOfficeDto boxOfficeDto = boxOfficeService.getBoxOfficeById(boxOfficeId);
        return boxOfficeDto;
    }

    @DeleteMapping("/{boxOfficeId}")
    public String deleteBoxOfficeById(@PathVariable long boxOfficeId){
        boxOfficeService.deleteBoxOfficeById(boxOfficeId);
        return String.format("delete boxOffice by Id: %d is Succesfull",boxOfficeId);
    }

    @PutMapping("/{boxOfficeId}")
    public BoxOfficeDto updateBoxOfficeById(@PathVariable long boxOfficeId, @RequestBody BoxOfficeDto boxOfficeDto){
        BoxOfficeDto updatedBoxOffice = boxOfficeService.updateBoxOfficeById(boxOfficeId, boxOfficeDto);
        return updatedBoxOffice;
    }

}
