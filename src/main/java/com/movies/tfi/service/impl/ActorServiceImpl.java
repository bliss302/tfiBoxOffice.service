package com.movies.tfi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.tfi.entity.Actor;
import com.movies.tfi.exception.ResourceNotFoundException;
import com.movies.tfi.payload.ActorDto;
import com.movies.tfi.repository.ActorRepository;
import com.movies.tfi.service.ActorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ActorDto createActor(ActorDto actorDto) throws ParseException {
        Actor actor = mapToEntity(actorDto);

        Actor newActor = actorRepository.save(actor);
        System.out.println("created actorId: "+newActor.getActorId());

        return mapToDto(newActor);
    }

    @Override
    public List<ActorDto> getAllActors(int pageNo, int pageSize, String sortBy, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?  Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Actor> actors;
        if(search == null){
            actors = actorRepository.findAll(pageable);
        }else {
            actors = actorRepository.searchActor(search,pageable);
        }
        //Page<Actor> actors = actorRepository.findAll(pageable);
        List<Actor> actorList = actors.getContent();

        return actorList.stream().map(actor -> mapToDto(actor) ).toList();
    }

    @Override
    public ActorDto findByActorId(long actorId) {
        Actor actor = actorRepository.findByActorId(actorId);
        if(actor ==null) {
            throw new ResourceNotFoundException("Actor", "actorId", actorId);
        }else return mapToDto(actor);
    }

    @Override
    public void deleteActorById(long actorId){
        actorRepository.deleteByActorId(actorId);
    }

    @Override
    public ActorDto updateActorById(long actorId, ActorDto actorDto) {
        Actor actor = actorRepository.findByActorId(actorId);
        if(actor==null){
            throw new RuntimeException();
        }

        actor.setActorId(actorDto.getActorId() !=0 ? actorDto.getActorId() : actor.getActorId());
        System.out.println(actorDto.getActorId());
        actor.setFirstName(actorDto.getFirstName() !=null && !actorDto.getFirstName().equals("") ? actorDto.getFirstName() : actor.getFirstName());
        actor.setLastName(actorDto.getLastName() != null && !actorDto.getLastName().equals("") ? actorDto.getLastName() : actor.getLastName());
        actor.setCategory(actorDto.getCategory() !=null && !actorDto.getCategory().equals("") ? actorDto.getCategory() : actor.getCategory());
        actor.setDateOfBirth(actorDto.getDateOfBirth() !=null && !actorDto.getDateOfBirth().equals("") ? actorDto.getDateOfBirth() : actor.getDateOfBirth());

        Actor updatedActor = actorRepository.save(actor);
        return mapToDto(updatedActor);
    }

    @Override
    public List<ActorDto> searchActorByFirstName(String text) {
        List<Actor> actors = actorRepository.searchActor(text);
        List<ActorDto> actorDtos = actors.stream().map(actor -> mapToDto(actor)).toList();
        return actorDtos;
    }

    private Actor mapToEntity(ActorDto actorDto){
        return new ObjectMapper().convertValue(actorDto, Actor.class);
    }

    //private
    public ActorDto mapToDto(Actor actor){
        return mapper.map(actor,ActorDto.class);
    }
}
