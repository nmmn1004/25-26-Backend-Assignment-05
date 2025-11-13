package com.gdg.blackjackapi.dto.User;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private String email;

    private String name;

    @SerializedName("verified_email")
    private Boolean verifiedEmail;

    @SerializedName("given_name")
    private String givenName;

    @SerializedName("family_name")
    private String familyName;

    @SerializedName("picture")
    private String pictureUrl;

    private String locale;
}
