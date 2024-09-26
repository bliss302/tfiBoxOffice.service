package com.movies.tfi.service.impl;

import com.movies.tfi.entity.Actor;
import com.movies.tfi.entity.BoxOffice;
import com.movies.tfi.entity.Movie;
import com.movies.tfi.payload.*;
import com.movies.tfi.repository.ActorRepository;
import com.movies.tfi.repository.BoxOfficeRepository;
import com.movies.tfi.repository.MovieRepository;
import com.movies.tfi.service.ActorService;
import com.movies.tfi.service.BoxOfficeService;
import com.movies.tfi.service.ExcelService;
import com.movies.tfi.service.MovieService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    ActorService actorService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    BoxOfficeRepository boxOfficeRepository;

    @Autowired
    BoxOfficeService boxOfficeService;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

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

    @Override
    public List<MovieDto> saveMoviesFromExcel(String file) {
        List<MovieDto> movieDtos = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if(!rows.hasNext()){
                throw new RuntimeException("Excel sheet is empty");
            }

            Row headerRow = sheet.getRow(0);
            Map<Integer,String> headerMap = new HashMap<>();
            for(Cell cell: headerRow){
                headerMap.put(cell.getColumnIndex(), cell.getStringCellValue());
            }

            for(int i=1; i<= sheet.getLastRowNum() ; i++){
                Row row = sheet.getRow(i);
                MovieDto movieDto = new MovieDto();
                setMovieDtoFields(movieDto,headerMap,row);
                movieDtos.add(movieDto);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Movie> movies = movieDtos.stream().map(ele -> movieService.mapToEntity(ele)).toList();
        List<Movie> newMovies = movieRepository.saveAll(movies);
        List<MovieDto> movieDtoList = newMovies.stream().map(ele -> movieService.mapToDto(ele)).toList();
        return movieDtoList;
    }

    @Override
    public List<BoxOfficeDto> saveBoxOfficeFromExcel(String file) {
        List<BoxOfficeDto> boxOfficeDtoList = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.rowIterator();
            if(!rows.hasNext()){
                throw new RuntimeException("Excel sheet is empty");
            }

            Map<Integer,String > headerMap = new HashMap<>();
            Row headerRow = sheet.getRow(0);
            for(Cell cell:headerRow){
                headerMap.put(cell.getColumnIndex(), cell.getStringCellValue());
            }

            Map<Long,Integer> ids = new HashMap<>();
//            for(int i=1; i<=sheet.getLastRowNum();i++){
                for(int i=1; i<3;i++){
                Row row = sheet.getRow(i);
                long boxOfficeId = (long)row.getCell(0).getNumericCellValue();
                BoxOfficeDto boxOfficeDto = new BoxOfficeDto();
                if(ids.containsKey(boxOfficeId)){
                    boxOfficeDto = boxOfficeDtoList.get(ids.get(boxOfficeId));
                }
                setBoxOfficeDtoFields(boxOfficeDto,headerMap,row);
                if(!ids.containsKey(boxOfficeId)){
                    int index = (ids.size()<1)? 0: ids.size()-1;
                    ids.put(boxOfficeId,index);
                    boxOfficeDtoList.add(boxOfficeDto);
                }

            }

        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
        List<BoxOffice> boxOffices = boxOfficeDtoList.stream().map(ele -> boxOfficeService.mapToEntity(ele)).toList();
        List<BoxOffice> newBoxOffices= boxOfficeRepository.saveAll(boxOffices);
        List<BoxOfficeDto> boxOfficeDtos = newBoxOffices.stream().map(ele -> boxOfficeService.mapToDto(ele)).toList();
        return boxOfficeDtos;
    }

    private void setMovieDtoFields(MovieDto movieDto, Map<Integer,String > headerMap, Row row){
        for(Cell cell:row) {
            String header = headerMap.get(cell.getColumnIndex());
            if(movieDto.getCast() ==null){
                movieDto.setCast(new CastDto());
            }
            if (header.equals("heroIds")) {
                List<Long> values = getValues(cell);
                movieDto.getCast().setHeroIds(values);
            } else if (header.equals("heroineIds")) {
                List<Long> values = getValues(cell);
                movieDto.getCast().setHeroIds(values);
            } else if (header.equals("otherActors")) {
                List<Long> values = getValues(cell);
                movieDto.getCast().setHeroIds(values);
            } else if (header.equals("directorId")) {
                movieDto.getCast().setDirectorId((long)cell.getNumericCellValue());
            } else {
                try {
                    Field field = movieDto.getClass().getDeclaredField(header);
                    field.setAccessible(true);


                    if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                        field.set(movieDto, (long)cell.getNumericCellValue());
                    } else if (field.getType().equals(String.class)) {
                        field.set(movieDto, cell.getStringCellValue());
                    } else if (field.getType().equals(Date.class)) {
                        field.set(movieDto, cell.getDateCellValue());
                    } else if (field.getType().equals(List.class)) {
                        String[] values = cell.getStringCellValue().split(",");
                        List<String> list = Arrays.asList(values);
                        field.set(movieDto, list);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void setBoxOfficeDtoFields(BoxOfficeDto boxOfficeDto, Map<Integer,String > headerMap, Row row) {
        String regex = "\\d+\\.?\\d*";
        Pattern pattern = Pattern.compile(regex);
        String type = null;
        for (Cell cell : row) {
            String header = headerMap.get(cell.getColumnIndex());

            if (header.equals("type")) {
                type = cell.getStringCellValue();
                continue;
            }
            try {
                Field field = boxOfficeDto.getClass().getDeclaredField(header);
                field.setAccessible(true);

                if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                    field.set(boxOfficeDto, (long) cell.getNumericCellValue());
                } else if (field.getType().equals(String.class)) {
                    field.set(boxOfficeDto, cell.getStringCellValue());
                } else if (field.getType().equals(RegionDto.class)) {
                    RegionDto regionDto = new RegionDto();
                    if(field.get(boxOfficeDto)!=null){
                        regionDto = (RegionDto) field.get(boxOfficeDto);
                    }
                    Field regionField = regionDto.getClass().getDeclaredField(type);
                    regionField.setAccessible(true);
                    String value = cell.getStringCellValue();
                    Matcher matcher = pattern.matcher(value);
                    BigDecimal num = null;
                    while(matcher.find()) {
                        num = BigDecimal.valueOf(Double.parseDouble(matcher.group()) * 10000000);
                    }
                    if(num==null) continue;
                    regionField.set(regionDto, num);
                    field.set(boxOfficeDto, regionDto);
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Long> getValues(Cell cell){
        List<Long> values = new ArrayList<>();
        if(cell.getCellType().equals(CellType.NUMERIC)){
            values.add((long)cell.getNumericCellValue());
        }
        else if (cell.getCellType().equals(CellType.STRING)){
            values = Arrays.stream(cell.getStringCellValue().replaceAll("\"","" ).split(","))
                    .map(Long::parseLong)
                    .toList();
        }
        return values;
    }
}
