package com.mycompany.jobsapi.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "jobs", type = "jobs")
public class Job {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;
    
    @Field(type = FieldType.Text)
    private String company;
    
    @Field(type = FieldType.Text)
    private String logoUrl;
    
    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Date)
    private String createDate;

    public Job() {
        this.createDate = Instant.ofEpochSecond(Instant.now().getEpochSecond()).toString();
    }

}
