package com.mycompany.jobsapi.rest;

import com.mycompany.jobsapi.mapper.JobMapper;
import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.rest.dto.CreateJobDto;
import com.mycompany.jobsapi.rest.dto.JobDto;
import com.mycompany.jobsapi.rest.dto.SearchDto;
import com.mycompany.jobsapi.rest.dto.UpdateJobDto;
import com.mycompany.jobsapi.service.JobService;
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

import static com.mycompany.jobsapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

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
    public Page<JobDto> getJobs(
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = {"createDate"}, direction = Direction.DESC) Pageable pageable,
            Principal principal) {
        log.info("Request to get a page of jobs (offset = {}, pageSize = {}) made by {}", pageable.getOffset(), pageable.getPageSize(), principal.getName());
        return jobService.getJobsByPage(pageable).map(jobMapper::toJobDto);
    }

    @Operation(summary = "Get the newest jobs")
    @GetMapping("/newest")
    public List<JobDto> getNewestJobs(@RequestParam(value = "number", required = false, defaultValue = "4") int number) {
        if (number > 10) {
            log.warn("The parameter number cannot be bigger than 10");
            number = 10;
        }
        return jobService.getNewestJobs(number)
                .stream()
                .map(jobMapper::toJobDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Get a job by id",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{id}")
    public JobDto getJobById(@PathVariable String id, Principal principal) {
        log.info("Request to get a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        return jobMapper.toJobDto(job);
    }

    @Operation(
            summary = "Create a job",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JobDto createJob(@Valid @RequestBody CreateJobDto createJobDto, Principal principal) {
        log.info("Request to create a job made by {}", principal.getName());
        Job job = jobMapper.toJob(createJobDto);
        job = jobService.saveJob(job);
        return jobMapper.toJobDto(job);
    }

    @Operation(
            summary = "Delete a job",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public JobDto deleteJob(@PathVariable String id, Principal principal) {
        log.info("Request to delete a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        jobService.deleteJob(job);
        return jobMapper.toJobDto(job);
    }

    @Operation(
            summary = "Update a job",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/{id}")
    public JobDto updateJob(@PathVariable String id, @Valid @RequestBody UpdateJobDto updateJobDto, Principal principal) {
        log.info("Request to update a job with id {} made by {}", id, principal.getName());
        Job job = jobService.validateAndGetJobById(id);
        jobMapper.updateJobFromDto(updateJobDto, job);
        jobService.saveJob(job);
        return jobMapper.toJobDto(job);
    }

    @Operation(
            summary = "Search for jobs",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/search")
    public Page<Job> searchJobs(@Valid @RequestBody SearchDto searchDto,
                                @ParameterObject @PageableDefault(size = 10, page = 0, sort = {"createDate"}, direction = Direction.DESC) Pageable pageable,
                                Principal principal) {
        log.info("Request to search a job with text {} made by {}", searchDto.getText(), principal.getName());
        return jobService.search(searchDto.getText(), pageable);
    }

}
