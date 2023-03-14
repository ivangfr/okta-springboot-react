package com.ivanfranchin.jobsapi.mapper;

import com.ivanfranchin.jobsapi.model.Job;
import com.ivanfranchin.jobsapi.rest.dto.CreateJobRequest;
import com.ivanfranchin.jobsapi.rest.dto.JobResponse;
import com.ivanfranchin.jobsapi.rest.dto.UpdateJobRequest;

public interface JobMapper {

    JobResponse toJobResponse(Job job);

    Job toJob(CreateJobRequest createJobRequest);

    void updateJobFromRequest(UpdateJobRequest updateJobRequest, Job job);
}