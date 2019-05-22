package com.mycompany.jobsapi.service;

import com.mycompany.jobsapi.exception.JobNotFoundException;
import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getLast6Jobs() {
        return jobRepository.findTop6ByOrderByCreateDateDesc();
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAllByOrderByCreateDateDesc();
    }

    @Override
    public Job validateAndGetJobById(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(String.format("Job with id %s not found", id)));
    }

    @Override
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Job job) {
        jobRepository.delete(job);
    }

}
