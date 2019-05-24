package com.mycompany.jobsapi.rest.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateJobDto {

    @NotBlank
    @ApiModelProperty(example = "Software Developer")
    private String title;

    @NotBlank
    @ApiModelProperty(position = 2, example = "Google")
    private String company;

    @ApiModelProperty(position = 3, example = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Google_2015_logo.svg")
    private String logoUrl;

    @NotBlank
    @ApiModelProperty(position = 4, example = "Software Developer with more than 5 years experience")
    private String description;

}
