package com.codingbat.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProblemDto {
    @NotNull(message = "name cannot be empty")
    private String name;

    @NotNull(message = "body cannot be empty")
    private String body;
}
