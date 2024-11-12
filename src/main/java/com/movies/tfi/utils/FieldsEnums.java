package com.movies.tfi.utils;

import com.movies.tfi.entity.Region;

public class FieldsEnums {

    public enum MovieFields {
        MOVIE_ID("movieId"),
        TITLE("title"),
        LANGUAGE("language"),
        GENRES("genres"),
        RELEASE_DATE("releaseDate"),
        BOX_OFFICE_ID("boxOfficeId"),
        CAST("cast");

        private String field;

        MovieFields(String field) {
            this.field = field;
        }

        public String toString() {
            return this.field;
        }
    }

    public enum BoxOfficeFields {
        BOX_OFFICE_ID("boxOfficeId"),
        MOVIE_ID("movieId"),

        BREAK_EVEN("breakEven"),
        AP("ap"),
        NIZAM("nizam"),
        VIZAG("vizag"),
        EAST("east"),
        WEST("west"),
        KRISHNA("krishna"),
        GUNTUR("guntur"),
        NELLORE("nellore"),
        CEDED("ceded"),
        KARNATAKA("karnataka"),
        TAMIL_NADU("tamilNadu"),
        KERALA("kerala"),
        HINDI_BELT("hindiBelt"),
        OVERSEAS("Overseas"),
        WORLD_WIDE_THEATRICAL("WorldWideTheatrical");

        private String field;

        BoxOfficeFields(String field) {
            this.field = field;
        }

        public String toString() {
            return this.field;
        }
    }

    public enum BoxOfficeCategory{
        GROSS("gross"),
        SHARE("share"),
        DAY1("day1");

        private String type;
        BoxOfficeCategory(String type){
            this.type = type;
        }

        public String toString(){
            return this.type;
        };
    }
}
