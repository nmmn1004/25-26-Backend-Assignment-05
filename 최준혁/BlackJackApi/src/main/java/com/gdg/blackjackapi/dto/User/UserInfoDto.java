package com.gdg.blackjackapi.dto.User;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDto {
    private String email;

    private String name;

    private Boolean verifiedEmail;

    private String givenName;

    private String familyName;

    @SerializedName("picture")
    private String pictureUrl;

    private String locale;
}
