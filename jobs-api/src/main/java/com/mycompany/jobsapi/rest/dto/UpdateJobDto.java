package com.mycompany.jobsapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateJobDto {

    @ApiModelProperty(example = "Senior Software Developer")
    private String title;

    @ApiModelProperty(position = 2, example = "Facebook")
    private String company;

    @ApiModelProperty(position = 3, example = "https://upload.wikimedia.org/wikipedia/commons/8/89/Facebook_Logo_%282019%29.svg")
    private String logoUrl;

    @ApiModelProperty(position = 4, example = "Senior Software Developer with more than 8 years experience")
    private String description;

}
