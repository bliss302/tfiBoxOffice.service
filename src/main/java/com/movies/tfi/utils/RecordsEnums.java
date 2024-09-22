package com.movies.tfi.utils;

public class RecordsEnums {
    public enum SearchType{
        HOME_RECORDS("_homeRecords"),
        MOVIE("_movie");
        private final String type;
        SearchType(String type){
            this.type = type;
        }

        public String getType(){
            return this.type;
        }
    }
}
