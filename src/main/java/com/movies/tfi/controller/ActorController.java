package com.movies.tfi.controller;

import com.movies.tfi.payload.ActorDto;
import com.movies.tfi.payload.Response;
import com.movies.tfi.service.ActorService;
import com.movies.tfi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("tfi/actor")
public class ActorController {

    @Autowired
    ActorService actorService;

    //controller <--> service <--> repository

    @PostMapping()
    public ResponseEntity<ActorDto> createActor(@RequestBody ActorDto actorDto) throws ParseException {
        System.out.println("creating actor...");
        ActorDto newActorDto = actorService.createActor(actorDto);
        return new ResponseEntity<>(newActorDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public List<ActorDto> getAllActors(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_ACTOR, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir,
            @RequestParam(value = "search", required = false) String search
    ){
        System.out.println("getting all actors...");
        return actorService.getAllActors(pageNo,pageSize,sortBy,sortDir,search);
    }

    @GetMapping("/{actorId}")
    public Response<ActorDto> getActorById(@PathVariable long actorId){
        ActorDto actorDto = actorService.findByActorId(actorId);

        Response<ActorDto> response = Response.<ActorDto>builder()
                .data(actorDto)
                .message("succesful")
                .statusCode(200)
                .build();
        return response;
    }

    @GetMapping("/search/{text}")
    public List<ActorDto> getActorBySearch(@PathVariable String text){
        return actorService.searchActorByFirstName(text);
    }

    @DeleteMapping("/{actorId}")
    public String deleteActorById(@PathVariable long actorId){
        actorService.deleteActorById(actorId);
        return String.format("delete actor by Id: %d is Succesfull",actorId);
    }

    @PutMapping("/{actorId}")
    public ResponseEntity<ActorDto> updateActorById(@PathVariable long actorId, @RequestBody ActorDto actorDto){
        ActorDto updatedActorDto = actorService.updateActorById(actorId,actorDto);
        return ResponseEntity.ok(updatedActorDto);
    }
}

