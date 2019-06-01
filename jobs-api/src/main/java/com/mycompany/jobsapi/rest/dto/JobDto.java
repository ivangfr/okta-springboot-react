package com.mycompany.jobsapi.rest.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class JobDto {

    private String id;
    private String title;
    private String company;
    private String logoUrl;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date createDate;

}
