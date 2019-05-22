package com.mycompany.jobsapi.service;

import com.mycompany.jobsapi.model.Job;

import java.util.List;

public interface JobService {

    List<Job> getLast6Jobs();

    List<Job> getAllJobs();

    Job validateAndGetJobById(String id);

    Job saveJob(Job job);

    void deleteJob(Job job);

}
