package com.movies.tfi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.tfi.payload.ActorDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class TfiApplicationTests {


	ModelMapper mapper;
	@Test
	void contextLoads() {
	}

	@Test
	public void testDateDeserialization() throws JsonProcessingException, ParseException {
		String json = "{\"dateOfBirth\":\"1983-05-20\"}";
		ActorDto actorDto = new ObjectMapper().readValue(json, ActorDto.class);
		Date expectedDate = new SimpleDateFormat("yyyy-MM-dd").parse("1983-05-20");

//		assertEquals(expectedDate, actorDTO.getDateOfBirth());
	}

}
