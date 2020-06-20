package com.mycompany.jobsapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateJobDto {

    @Schema(example = "Software Developer")
    @NotBlank
    private String title;

    @Schema(example = "Google")
    @NotBlank
    private String company;

    @Schema(example = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/500px-Google_2015_logo.svg.png")
    private String logoUrl;

    @Schema(example = "Software Developer with more than 5 years experience")
    @NotBlank
    private String description;

}
