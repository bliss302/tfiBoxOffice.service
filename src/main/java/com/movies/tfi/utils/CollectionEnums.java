package com.movies.tfi.utils;

public class CollectionEnums {
    public enum Collections{
        MOVIES("movies"),
        ACTORS("actors"),
        BOX_OFFICE("boxOffice");
        private String name;

        Collections (String name){
            this.name = name;
        }

        public String toName(){
            return this.name;
        }
    }
}
