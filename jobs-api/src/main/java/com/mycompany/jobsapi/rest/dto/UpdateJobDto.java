package com.mycompany.jobsapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateJobDto {

    @Schema(example = "Senior Software Developer")
    private String title;

    @Schema(example = "Facebook")
    private String company;

    @Schema(example = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Facebook_Logo_%282019%29.svg/500px-Facebook_Logo_%282019%29.svg.png")
    private String logoUrl;

    @Schema(example = "Senior Software Developer with more than 8 years experience")
    private String description;

}
