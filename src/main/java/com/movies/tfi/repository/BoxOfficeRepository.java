package com.movies.tfi.repository;

import com.movies.tfi.entity.BoxOffice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoxOfficeRepository extends MongoRepository<BoxOffice,Long> {
    public BoxOffice findBoxOfficeByBoxOfficeId(long boxOfficeId);
    public void deleteBoxOfficeByBoxOfficeId(long boxOfficeId);
}
