package com.mycompany.jobsapi.rest.dto;

import lombok.Value;

@Value
public class JobResponse {

    String id;
    String title;
    String company;
    String logoUrl;
    String description;
    String createDate;
}
