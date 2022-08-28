package com.ivanfranchin.jobsapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchRequest {

    @NotBlank
    @Schema(title = "text to be searched", example = "\"java developer\" AND (google OR facebook) NOT junior")
    private String text;
}