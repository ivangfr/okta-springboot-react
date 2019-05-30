package com.mycompany.jobsapi.repository;

import java.util.List;

import com.mycompany.jobsapi.model.Job;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {

    List<Job> findTopByOrderByCreateDateDesc(Pageable pageable);

}
