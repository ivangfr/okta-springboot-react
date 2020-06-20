package com.mycompany.jobsapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@Document(indexName = "jobs")
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
