package com.movies.tfi.service.impl;

import com.movies.tfi.exception.ResourceNotFoundException;
import com.movies.tfi.payload.MovieResult;
import com.movies.tfi.payload.TMDBIResponse;
import com.movies.tfi.service.TMDBIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TMDBIServiceImpl implements TMDBIService {
    public static final String TMDBI_URL = "https://api.themoviedb.org/3/search/movie?api_key=API_KEY&query=MovieName";
    public static final String API_KEY = "c0428ee6bb247a880915ad8a7e62754f";

    @Autowired
    RestTemplate restTemplate;

    public MovieResult getMoviePoster(String movieName){
        String url = TMDBI_URL.replace("API_KEY",API_KEY).replace("MovieName",movieName);
        ResponseEntity<TMDBIResponse> response = restTemplate.exchange(url, HttpMethod.GET,null, TMDBIResponse.class);
        TMDBIResponse tmdbiResponse = response.getBody();
        if(tmdbiResponse.getTotal_results() ==0){
            throw new ResourceNotFoundException("movie","title",movieName);
        }
        return tmdbiResponse.getResults().get(0);
    }

}
