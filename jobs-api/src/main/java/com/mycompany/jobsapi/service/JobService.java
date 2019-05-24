package com.mycompany.jobsapi.service;

import java.util.List;

import com.mycompany.jobsapi.model.Job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    List<Job> getLast6Jobs();

    Page<Job> getAllJobsByPage(Pageable pageable);

    Job validateAndGetJobById(String id);

    Job saveJob(Job job);

    void deleteJob(Job job);

}
