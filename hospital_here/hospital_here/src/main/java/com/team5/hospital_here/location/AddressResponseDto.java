package com.team5.hospital_here.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AddressResponseDto {

    @JsonProperty("results")
    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public static class Results {
        @JsonProperty("juso")
        private List<Juso> juso;

        public List<Juso> getJuso() {
            return juso;
        }

        public void setJuso(List<Juso> juso) {
            this.juso = juso;
        }
    }

    public static class Juso {
        @JsonProperty("roadAddr")
        private String roadAddr;

        @JsonProperty("jibunAddr")
        private String jibunAddr;

        public String getRoadAddr() {
            return roadAddr;
        }

        public void setRoadAddr(String roadAddr) {
            this.roadAddr = roadAddr;
        }

        public String getJibunAddr() {
            return jibunAddr;
        }

        public void setJibunAddr(String jibunAddr) {
            this.jibunAddr = jibunAddr;
        }
    }
}

