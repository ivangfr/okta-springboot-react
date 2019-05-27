package com.mycompany.jobsapi.rest;

import java.security.Principal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.rest.dto.CreateJobDto;
import com.mycompany.jobsapi.rest.dto.JobDto;
import com.mycompany.jobsapi.rest.dto.UpdateJobDto;
import com.mycompany.jobsapi.service.JobService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Slf4j
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final MapperFacade mapperFacade;

    public JobController(JobService jobService, MapperFacade mapperFacade) {
        this.jobService = jobService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping
    public Page<JobDto> getAllJobs(Pageable pageable, Principal principal) {
        log.info("Request to get a page of jobs made by {}", principal.getName());
        Page<Job> pageJobs = jobService.getAllJobsByPage(pageable);
        return pageJobs.map(new Function<Job,JobDto>() {
            @Override
            public JobDto apply(Job job) {
                return mapperFacade.map(job, JobDto.class);
            }
        });
    }

    @GetMapping("/last6")
    public List<JobDto> getLast6Jobs() {
        return jobService.getLast6Jobs()
                .stream()
                .map(job -> mapperFacade.map(job, JobDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public JobDto getJobById(@PathVariable String id, Principal principal) {
        log.info("Request to get a job made by {}", principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        return mapperFacade.map(job, JobDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JobDto createJob(@Valid @RequestBody CreateJobDto createJobDto, Principal principal) {
        log.info("Request to create a job made by {}", principal.getName());
        Job job = mapperFacade.map(createJobDto, Job.class);
        job = jobService.saveJob(job);
        return mapperFacade.map(job, JobDto.class);
    }

    @DeleteMapping("/{id}")
    public JobDto deleteJob(@PathVariable String id, Principal principal) {
        log.info("Request to delete a job made by {}", principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        jobService.deleteJob(job);
        return mapperFacade.map(job, JobDto.class);
    }

    @PutMapping("/{id}")
    public JobDto updateJob(@PathVariable String id, @Valid @RequestBody UpdateJobDto updateJobDto, Principal principal) {
        log.info("Request to update a job made by {}", principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        mapperFacade.map(updateJobDto, job);
        jobService.saveJob(job);
        return mapperFacade.map(job, JobDto.class);
    }

}
