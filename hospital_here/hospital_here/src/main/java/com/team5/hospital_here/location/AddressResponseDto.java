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

        @JsonProperty("common")
        private Common common;

        public List<Juso> getJuso() {
            return juso;
        }

        public void setJuso(List<Juso> juso) {
            this.juso = juso;
        }

        public Common getCommon() {
            return common;
        }

        public void setCommon(Common common) {
            this.common = common;
        }
    }

    public static class Common {
        @JsonProperty("totalCount")
        private String totalCount;

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
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
