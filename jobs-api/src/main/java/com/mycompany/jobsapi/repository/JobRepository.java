package com.mycompany.jobsapi.repository;

import com.mycompany.jobsapi.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobRepository extends ElasticsearchRepository<Job, String> {

    @Query("{ \"query_string\" : { \"query\" : \"?0\" } }")
    Page<Job> findJobsUsingQueryStringQuery(String text, Pageable pageable);

}
