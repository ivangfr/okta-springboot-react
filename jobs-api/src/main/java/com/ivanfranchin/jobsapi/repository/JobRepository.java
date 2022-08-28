package com.ivanfranchin.jobsapi.repository;

import com.ivanfranchin.jobsapi.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends ElasticsearchRepository<Job, String> {

    @Query("{ \"query_string\" : { \"query\" : \"?0\" } }")
    Page<Job> findJobsUsingQueryStringQuery(String text, Pageable pageable);
}
