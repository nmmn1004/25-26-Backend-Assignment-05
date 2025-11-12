package com.gdg.blackjackapi.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private String email;
    private String name;

    private Boolean verifiedEmail;
    private String givenName;
    private String familyName;
    private String pictureUrl;
    private String locale;
}