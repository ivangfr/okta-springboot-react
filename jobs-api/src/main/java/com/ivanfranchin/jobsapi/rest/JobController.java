package com.ivanfranchin.jobsapi.rest;

import com.ivanfranchin.jobsapi.mapper.JobMapper;
import com.ivanfranchin.jobsapi.rest.dto.CreateJobRequest;
import com.ivanfranchin.jobsapi.rest.dto.JobResponse;
import com.ivanfranchin.jobsapi.service.JobService;
import com.ivanfranchin.jobsapi.model.Job;
import com.ivanfranchin.jobsapi.rest.dto.SearchRequest;
import com.ivanfranchin.jobsapi.rest.dto.UpdateJobRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
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

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.ivanfranchin.jobsapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final JobMapper jobMapper;

    @Operation(
            summary = "Get jobs with pagination",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public Page<JobResponse> getJobs(
            @ParameterObject @PageableDefault(sort = {"createDate"}, direction = Direction.DESC) Pageable pageable,
            Principal principal) {
        log.info("Request to get a page of jobs (offset = {}, pageSize = {}) made by {}",
                pageable.getOffset(), pageable.getPageSize(), principal.getName());
        return jobService.getJobsByPage(pageable).map(jobMapper::toJobResponse);
    }

    @Operation(summary = "Get the newest jobs")
    @GetMapping("/newest")
    public List<JobResponse> getNewestJobs(@RequestParam(value = "number", required = false, defaultValue = "4") int number) {
        if (number > 10) {
            log.warn("The parameter number cannot be bigger than 10");
            number = 10;
        }
        return jobService.getNewestJobs(number)
                .stream()
                .map(jobMapper::toJobResponse)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Get a job by id",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{id}")
    public JobResponse getJobById(@PathVariable String id, Principal principal) {
        log.info("Request to get a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        return jobMapper.toJobResponse(job);
    }

    @Operation(
            summary = "Create a job",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JobResponse createJob(@Valid @RequestBody CreateJobRequest createJobRequest, Principal principal) {
        log.info("Request to create a job made by {}", principal.getName());
        Job job = jobMapper.toJob(createJobRequest);
        job = jobService.saveJob(job);
        return jobMapper.toJobResponse(job);
    }

    @Operation(
            summary = "Delete a job",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public JobResponse deleteJob(@PathVariable String id, Principal principal) {
        log.info("Request to delete a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        jobService.deleteJob(job);
        return jobMapper.toJobResponse(job);
    }

    @Operation(
            summary = "Update a job",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/{id}")
    public JobResponse updateJob(@PathVariable String id,
                                 @Valid @RequestBody UpdateJobRequest updateJobRequest, Principal principal) {
        log.info("Request to update a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        jobMapper.updateJobFromRequest(updateJobRequest, job);
        jobService.saveJob(job);
        return jobMapper.toJobResponse(job);
    }

    @Operation(
            summary = "Search for jobs",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/search")
    public Page<Job> searchJobs(@Valid @RequestBody SearchRequest searchRequest,
                                @ParameterObject @PageableDefault(sort = {"createDate"}, direction = Direction.DESC) Pageable pageable,
                                Principal principal) {
        log.info("Request to search a job with text {} made by {}", searchRequest.getText(), principal.getName());
        return jobService.search(searchRequest.getText(), pageable);
    }
}
