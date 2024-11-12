package com.movies.tfi.service;

import com.movies.tfi.entity.Actor;
import com.movies.tfi.payload.ActorDto;

import java.text.ParseException;
import java.util.List;

public interface ActorService {
    public ActorDto createActor(ActorDto actorDto) throws ParseException;
    public List<ActorDto> getAllActors(int pageNo, int pageSize, String sortBy, String sortDir, String search);
    public ActorDto findByActorId(long id);
    public void deleteActorById(long id);
    public ActorDto updateActorById(long id, ActorDto actorDto);
    public List<ActorDto> searchActorByFirstName(String text);
    public ActorDto mapToDto(Actor actor);
}
