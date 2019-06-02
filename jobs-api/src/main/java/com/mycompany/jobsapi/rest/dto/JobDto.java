package com.mycompany.jobsapi.rest.dto;

import lombok.Data;

@Data
public class JobDto {

    private String id;
    private String title;
    private String company;
    private String logoUrl;
    private String description;
    private String createDate;

}
