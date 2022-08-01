package com.codingbat.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class LanguageDto {

    @NotNull(message = "name cannot be empty")
    private  String name;

    private boolean active = true;
}
