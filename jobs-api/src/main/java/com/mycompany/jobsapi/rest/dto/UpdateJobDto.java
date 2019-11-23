package com.mycompany.jobsapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateJobDto {

    @ApiModelProperty(example = "Senior Software Developer")
    private String title;

    @ApiModelProperty(position = 1, example = "Facebook")
    private String company;

    @ApiModelProperty(position = 2, example = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Facebook_Logo_%282019%29.svg/500px-Facebook_Logo_%282019%29.svg.png")
    private String logoUrl;

    @ApiModelProperty(position = 3, example = "Senior Software Developer with more than 8 years experience")
    private String description;

}
