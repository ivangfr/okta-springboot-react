package com.mycompany.jobsapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchDto {

    @ApiModelProperty(value = "text to be searched", example = "google")
    @NotBlank
    private String text;

}