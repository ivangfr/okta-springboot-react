package com.mycompany.jobsapi.service;

import java.util.List;

import com.mycompany.jobsapi.exception.JobNotFoundException;
import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.repository.JobRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<Job> getNewestJobs(int number) {
        return jobRepository.findAll(PageRequest.of(0, number, Sort.by("createDate").descending())).getContent();
    }

    @Override
    public Page<Job> getJobsByPage(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public Job validateAndGetJobById(String id) {
        return jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(id));
    }

    @Override
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Job job) {
        jobRepository.delete(job);
    }

    @Override
    public Page<Job> search(String text, Pageable pageable) {
        return jobRepository.findJobsUsingQueryStringQuery(text, pageable);
    }

}
