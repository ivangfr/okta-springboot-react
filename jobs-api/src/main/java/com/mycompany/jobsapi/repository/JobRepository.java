package com.mycompany.jobsapi.repository;

import java.util.List;

import com.mycompany.jobsapi.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {

    List<Job> findTop6ByOrderByCreateDateDesc();

    List<Job> findAllByOrderByCreateDateDesc();

}
