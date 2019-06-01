package com.mycompany.jobsapi.repository;

import com.mycompany.jobsapi.model.Job;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobRepository extends ElasticsearchRepository<Job, String> {

}
