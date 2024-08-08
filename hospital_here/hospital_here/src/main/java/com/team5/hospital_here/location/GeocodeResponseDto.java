package com.team5.hospital_here.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeocodeResponseDto {

    @JsonProperty("status")
    private String status;

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("addresses")
    private List<Address> addresses;

    public static class Meta {
        @JsonProperty("totalCount")
        private int totalCount;

        @JsonProperty("page")
        private int page;

        @JsonProperty("count")
        private int count;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        @JsonProperty("roadAddress")
        private String roadAddress;

        @JsonProperty("jibunAddress")
        private String jibunAddress;

        @JsonProperty("x")
        private double x; // 경도

        @JsonProperty("y")
        private double y; // 위도

        @JsonProperty("postalCode")
        private String postalCode; // 추가된 필드

        @JsonProperty("addressElements")
        private void unpackPostalCode(List<AddressElement> addressElements) {
            for (AddressElement element : addressElements) {
                if (element.getTypes().contains("POSTAL_CODE")) {
                    this.postalCode = element.getLongName();
                    break;
                }
            }
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddressElement {
        @JsonProperty("types")
        private List<String> types;

        @JsonProperty("longName")
        private String longName;

        @JsonProperty("shortName")
        private String shortName;

        @JsonProperty("code")
        private String code;
    }
}
