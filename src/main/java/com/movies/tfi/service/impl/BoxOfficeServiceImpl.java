package com.movies.tfi.service.impl;

import com.movies.tfi.entity.BoxOffice;
import com.movies.tfi.entity.Region;
import com.movies.tfi.exception.ResourceNotFoundException;
import com.movies.tfi.payload.BoxOfficeDto;
import com.movies.tfi.payload.GetRecordRequestDto;
import com.movies.tfi.payload.MetaData;
import com.movies.tfi.payload.Response;
import com.movies.tfi.repository.BoxOfficeRepository;
import com.movies.tfi.service.BoxOfficeService;
import com.movies.tfi.utils.CollectionEnums;
import com.movies.tfi.utils.FieldsEnums;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BoxOfficeServiceImpl implements BoxOfficeService {

    @Autowired
    BoxOfficeRepository boxOfficeRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public BoxOfficeDto createBoxOffice(BoxOfficeDto boxOfficeDto) {
        BoxOffice boxOffice = mapToEntity(boxOfficeDto);
        BoxOffice newBoxOffice = boxOfficeRepository.save(boxOffice);
        return mapToDto(newBoxOffice);
    }

    @Override
    public List<BoxOfficeDto> getAllBoxOffice(int pageNo, int pageSize, String  sortBy, String sortDir ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<BoxOffice> boxOfficeData = boxOfficeRepository.findAll(pageable);
        List<BoxOffice> boxOfficeList = boxOfficeData.getContent();
        List<BoxOfficeDto> boxOfficeDtoList = boxOfficeList.stream().map(this::mapToDto).toList();
        return boxOfficeDtoList;
    }

    @Override
    public BoxOfficeDto getBoxOfficeById(long boxOfficeId) {
        BoxOffice boxOffice = boxOfficeRepository.findBoxOfficeByBoxOfficeId(boxOfficeId);
        if(boxOffice == null){
            throw new ResourceNotFoundException("boxOffice", "boxOfficeId", boxOfficeId);
        }
        return mapToDto(boxOffice);
    }

    public Response<List<BoxOfficeDto>> getBoxOffice(GetRecordRequestDto getRecordRequestDto){

        if(getRecordRequestDto == null){
            throw new RuntimeException();
        }

        int pageNo = getRecordRequestDto.getPage().getPageNo();
        int pageSize = getRecordRequestDto.getPage().getSize();
        String sortBy = getRecordRequestDto.getSort().getSortBy();
        String sortDir = getRecordRequestDto.getSort().getSortDir();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Query query = getProjectionQuery(getRecordRequestDto.getMovieId(),getRecordRequestDto.getRegions(),getRecordRequestDto.getCategory());



        MetaData metaData = new MetaData();
        long totalRecords = mongoTemplate.count(query,BoxOffice.class, CollectionEnums.Collections.BOX_OFFICE.toName());

        query.with(pageable);
        query.with(sort);
        List<BoxOffice> boxOffice = mongoTemplate.find(query,BoxOffice.class, CollectionEnums.Collections.BOX_OFFICE.toName());

        metaData.setTotalItems(totalRecords);
        metaData.setHasMore((long) (pageNo + 1) * pageSize < totalRecords);

        List<BoxOfficeDto> boxOfficeDtoList = boxOffice.stream().map(this::mapToDto).toList();

        Response<List<BoxOfficeDto>> response = Response.<List<BoxOfficeDto>>builder()
                .data(boxOfficeDtoList)
                .metaData(metaData)
                .build();
        return response;
    }

    @Override
    public void deleteBoxOfficeById(long boxOfficeId) {
        boxOfficeRepository.deleteBoxOfficeByBoxOfficeId(boxOfficeId);
    }

    @Override
    public List<String> getAreas() {
        List<String> areas = Arrays.stream(BoxOffice.class.getDeclaredFields())
                .filter(element -> element.getType().equals(Region.class))
                .map(element -> element.getName())
                .toList();
        return areas;
    }

    @Override
    public List<String> getCategories() {
        List<String> categories = Arrays.stream(Region.class.getDeclaredFields())
                .filter(element -> element.getType().equals(BigDecimal.class))
                .map(element -> element.getName())
                .toList();
        return categories;
    }

    @Override
    public BoxOfficeDto updateBoxOfficeById(long boxOfficeId, BoxOfficeDto boxOfficeDto) {
        return null;
//        BoxOffice boxOffice = boxOfficeRepository.findBoxOfficeByBoxOfficeId(boxOfficeId);
//        if(boxOffice == null){
//            throw new ResourceNotFoundException("boxOffice", "boxOfficeId", boxOfficeId);
//        }
//
//        boxOffice.setBoxOfficeId(boxOffice.getBoxOfficeId() !=0 ? boxOffice.getBoxOfficeId() : boxOfficeDto.getBoxOfficeId());
//        boxOffice.setMovieId(boxOffice.getMovieId() !=0 ? boxOffice.getMovieId() : boxOfficeDto.getMovieId());
//        boxOffice.setType(boxOffice.getType() !=null & !boxOffice.equals("") ? boxOffice.getType() : boxOfficeDto.getType());
//
//        boxOffice.setBreakEven(getValueOrDefault(boxOfficeDto.getBreakEven(),boxOffice.getBreakEven()));
//        boxOffice.setAp(getValueOrDefault(boxOfficeDto.getAp(),boxOffice.getAp()));
//        boxOffice.setNizam(getValueOrDefault(boxOfficeDto.getNellore(),boxOffice.getNellore()));
//        boxOffice.setVizag(getValueOrDefault(boxOfficeDto.getVizag(),boxOffice.getVizag()));
//        boxOffice.setEast(getValueOrDefault(boxOfficeDto.getEast(),boxOffice.getEast()));
//        boxOffice.setWest(getValueOrDefault(boxOfficeDto.getWest(),boxOffice.getWest()));
//        boxOffice.setKrishna(getValueOrDefault(boxOfficeDto.getKrishna(),boxOffice.getKrishna()));
//        boxOffice.setGuntur(getValueOrDefault(boxOfficeDto.getGuntur(),boxOffice.getGuntur()));
//        boxOffice.setNellore(getValueOrDefault(boxOfficeDto.getNellore(),boxOffice.getNellore()));
//        boxOffice.setCeded(getValueOrDefault(boxOfficeDto.getCeded(),boxOffice.getCeded()));
//        boxOffice.setKarnataka(getValueOrDefault(boxOfficeDto.getKarnataka(),boxOffice.getKarnataka()));
//        boxOffice.setKerala(getValueOrDefault(boxOfficeDto.getKerala(),boxOffice.getKerala()));
//        boxOffice.setHindiBelt(getValueOrDefault(boxOfficeDto.getHindiBelt(),boxOffice.getHindiBelt()));
//        boxOffice.setOverseas(getValueOrDefault(boxOfficeDto.getOverseas(),boxOffice.getOverseas()));
//        boxOffice.setWorldWideTheatrical(getValueOrDefault(boxOfficeDto.getWorldWideTheatrical(),boxOffice.getWorldWideTheatrical()));
//
//        BoxOffice updatedBoxOffice = boxOfficeRepository.save(boxOffice);
//        return mapToDto(updatedBoxOffice);
    }

    //private
    private BoxOfficeDto mapToDto(BoxOffice boxOffice){
        return mapper.map(boxOffice,BoxOfficeDto.class);
    }

    private BoxOffice mapToEntity(BoxOfficeDto boxOfficeDto){
        return mapper.map(boxOfficeDto, BoxOffice.class);
    }

    public BigDecimal getValueOrDefault(BigDecimal newValue, BigDecimal currentValue){
        return newValue != null && newValue.compareTo(BigDecimal.ZERO) != 0 ? newValue : currentValue;
    }

    private Query getSearchByMovieIdQuery(long movieId){
        Query query = new Query();
        query.addCriteria(Criteria.where(FieldsEnums.BoxOfficeFields.MOVIE_ID.toString()).is(movieId));
        return query;
    }

    private Query getProjectionQuery(long movieId, List<String> regions,String category){
        Query query = new Query();
        if(movieId!=0){
            query.addCriteria(Criteria.where(FieldsEnums.BoxOfficeFields.MOVIE_ID.toString()).is(movieId));
        }
        query.fields().include(FieldsEnums.BoxOfficeFields.BOX_OFFICE_ID.toString())
                .include(FieldsEnums.BoxOfficeFields.MOVIE_ID.toString());

        List<Criteria> criteria = new ArrayList<>();
        for(String region:regions){
            String field = region + "." + category;
            query.fields().include(field);
            criteria.add(Criteria.where(field).exists(true).ne(null));
        }

        query.addCriteria(new Criteria().orOperator(criteria));
        return query;
    }
}
