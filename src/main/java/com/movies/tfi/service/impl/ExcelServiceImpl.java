package com.movies.tfi.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.movies.tfi.entity.Actor;
import com.movies.tfi.payload.ActorDto;
import com.movies.tfi.repository.ActorRepository;
import com.movies.tfi.service.ActorService;
import com.movies.tfi.service.ExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    ActorService actorService;

    @Override
    public List<ActorDto> saveActorsFromExcel(String file) {
        try(InputStream inputStream = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(inputStream) ) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if(!rows.hasNext()){
                throw new RuntimeException("Excel sheet is empty");
            }
            List<Actor> actors = new ArrayList<>();

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Actor actor = new Actor();
                actor.setActorId((long)row.getCell(0).getNumericCellValue());
                actor.setFirstName(row.getCell(1).getStringCellValue());
                actor.setLastName(row.getCell(2).getStringCellValue());
                actor.setCategory(row.getCell(3).getStringCellValue());
                actor.setDateOfBirth( row.getCell(4).getDateCellValue());

                actors.add(actor);
            }

            List<Actor> actorList = actorRepository.saveAll(actors);
            workbook.close();
            inputStream.close();
            List<ActorDto> actorDtos = actorList.stream().map(element -> actorService.mapToDto(element)).toList();
            return actorDtos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
    }
}
