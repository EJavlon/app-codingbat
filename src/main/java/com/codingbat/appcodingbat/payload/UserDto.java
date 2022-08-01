package com.codingbat.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull(message = "email cannot be empty")
    private String email;

    @NotNull(message = "password cannot be empty")
    private String password;
}
