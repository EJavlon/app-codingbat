package com.codingbat.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SolutionDto {
    @NotNull(message = "body cannot be empty")
    private String body;

    @NotNull(message = "problem id cannot be empty")
    private Integer problemId;
}
