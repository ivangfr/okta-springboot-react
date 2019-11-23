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
    @ApiModelProperty(position = 1, example = "Google")
    private String company;

    @ApiModelProperty(position = 2, example = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/500px-Google_2015_logo.svg.png")
    private String logoUrl;

    @NotBlank
    @ApiModelProperty(position = 3, example = "Software Developer with more than 5 years experience")
    private String description;

}
