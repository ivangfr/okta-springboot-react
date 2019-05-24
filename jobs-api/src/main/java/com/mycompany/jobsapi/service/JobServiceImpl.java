package com.mycompany.jobsapi.service;

import java.util.List;

import com.mycompany.jobsapi.exception.JobNotFoundException;
import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.repository.JobRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Job> getAllJobsByPage(Pageable pageable) {
        return jobRepository.findAllByOrderByCreateDateDesc(pageable);
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
