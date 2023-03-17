package com.ivanfranchin.jobsapi.mapper;

import com.ivanfranchin.jobsapi.model.Job;
import com.ivanfranchin.jobsapi.rest.dto.CreateJobRequest;
import com.ivanfranchin.jobsapi.rest.dto.JobResponse;
import com.ivanfranchin.jobsapi.rest.dto.UpdateJobRequest;
import org.springframework.stereotype.Service;

@Service
public class JobMapperImpl implements JobMapper {

    @Override
    public JobResponse toJobResponse(Job job) {
        if (job == null) {
            return null;
        }
        return new JobResponse(job.getId(), job.getTitle(), job.getCompany(), job.getLogoUrl(), job.getDescription(), job.getCreateDate());
    }

    @Override
    public Job toJob(CreateJobRequest createJobRequest) {
        if (createJobRequest == null) {
            return null;
        }
        return new Job(createJobRequest.getTitle(), createJobRequest.getCompany(), createJobRequest.getLogoUrl(), createJobRequest.getDescription());
    }

    @Override
    public void updateJobFromRequest(UpdateJobRequest updateJobRequest, Job job) {
        if (updateJobRequest == null) {
            return;
        }

        if (updateJobRequest.getTitle() != null) {
            job.setTitle(updateJobRequest.getTitle());
        }
        if (updateJobRequest.getCompany() != null) {
            job.setCompany(updateJobRequest.getCompany());
        }
        if (updateJobRequest.getLogoUrl() != null) {
            job.setLogoUrl(updateJobRequest.getLogoUrl());
        }
        if (updateJobRequest.getDescription() != null) {
            job.setDescription(updateJobRequest.getDescription());
        }
    }
}
