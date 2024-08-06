package com.team5.hospital_here.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeocodeResponseDto {

    @JsonProperty("status")
    private Status status;

    @JsonProperty("results")
    private Result[] results;


    public static class Status {
        @JsonProperty("code")
        private int code;

        @JsonProperty("name")
        private String name;

        @JsonProperty("message")
        private String message;


    }

    public static class Result {
        @JsonProperty("name")
        private String name;

        @JsonProperty("code")
        private Code code;

        @JsonProperty("region")
        private Region region;



        public static class Code {
            @JsonProperty("id")
            private String id;

            @JsonProperty("type")
            private String type;

            @JsonProperty("mappingId")
            private String mappingId;

        }

        public static class Region {
            @JsonProperty("area0")
            private Area area0;

            @JsonProperty("area1")
            private Area area1;

            @JsonProperty("area2")
            private Area area2;

            @JsonProperty("area3")
            private Area area3;

            @JsonProperty("area4")
            private Area area4;



            public static class Area {
                @JsonProperty("name")
                private String name;

                @JsonProperty("coords")
                private Coords coords;


                public static class Coords {
                    @JsonProperty("center")
                    private Center center;


                    public static class Center {
                        @JsonProperty("crs")
                        private String crs;

                        @JsonProperty("x")
                        private double x;

                        @JsonProperty("y")
                        private double y;

                    }
                }
            }
        }
    }
}
