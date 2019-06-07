package com.mycompany.jobsapi.rest;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.rest.dto.CreateJobDto;
import com.mycompany.jobsapi.rest.dto.JobDto;
import com.mycompany.jobsapi.rest.dto.SearchDto;
import com.mycompany.jobsapi.rest.dto.UpdateJobDto;
import com.mycompany.jobsapi.service.JobService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(
            value = "Get all jobs with pagination",
            notes = "To sort the results by a specified field (ex. 'createDate'), use in 'sort' field a string like: createDate,[asc|desc]")
    @GetMapping
    public Page<JobDto> getAllJobs(
            @PageableDefault(size=10, page=0, sort={"createDate"}, direction=Direction.DESC) Pageable pageable,
            Principal principal) {
        log.info("Request to get a page of jobs (offset = {}, pageSize = {}) made by {}", pageable.getOffset(), pageable.getPageSize(), principal.getName());
        return jobService.getAllJobsByPage(pageable).map(job -> mapperFacade.map(job, JobDto.class));
    }

    @ApiOperation("Get the newest jobs")
    @GetMapping("/newest")
    public List<JobDto> getNewestJobs(@RequestParam(value = "number", required = false, defaultValue = "4") int number) {
        if (number > 10) {
            log.warn("The parameter number cannot be bigger than 10");
            number = 10;
        }
        return jobService.getNewestJobs(number)
                .stream()
                .map(job -> mapperFacade.map(job, JobDto.class))
                .collect(Collectors.toList());
    }

    @ApiOperation("Get a job by id")
    @GetMapping("/{id}")
    public JobDto getJobById(@PathVariable String id, Principal principal) {
        log.info("Request to get a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        return mapperFacade.map(job, JobDto.class);
    }

    @ApiOperation("Create a job")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JobDto createJob(@Valid @RequestBody CreateJobDto createJobDto, Principal principal) {
        log.info("Request to create a job made by {}", principal.getName());
        Job job = mapperFacade.map(createJobDto, Job.class);
        job = jobService.saveJob(job);
        return mapperFacade.map(job, JobDto.class);
    }

    @ApiOperation("Delete a job")
    @DeleteMapping("/{id}")
    public JobDto deleteJob(@PathVariable String id, Principal principal) {
        log.info("Request to delete a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        jobService.deleteJob(job);
        return mapperFacade.map(job, JobDto.class);
    }

    @ApiOperation("Update a job")
    @PutMapping("/{id}")
    public JobDto updateJob(@PathVariable String id, @Valid @RequestBody UpdateJobDto updateJobDto, Principal principal) {
        log.info("Request to update a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        mapperFacade.map(updateJobDto, job);
        jobService.saveJob(job);
        return mapperFacade.map(job, JobDto.class);
    }

    @ApiOperation(
            value = "Search jobs",
            notes = "This endpoint does a query for the 'string' informed in the Job fields: 'title', 'company' and 'description'\n" +
                    "To sort the results by a specified field (ex. 'createDate'), use in 'sort' field a string like: createDate,[asc|desc]")
    @PutMapping("/search")
    public Page<Job> searchNews(@Valid @RequestBody SearchDto searchDto, Principal principal,
        @PageableDefault(size=10, page=0, sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        log.info("Request to search a job with text {} made by {}", searchDto.getText(), principal.getName());
        return jobService.search(searchDto.getText(), pageable);
    }

}
