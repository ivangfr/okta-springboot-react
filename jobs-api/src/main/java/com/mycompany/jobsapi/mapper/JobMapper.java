package com.mycompany.jobsapi.mapper;

import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.rest.dto.CreateJobRequest;
import com.mycompany.jobsapi.rest.dto.JobResponse;
import com.mycompany.jobsapi.rest.dto.UpdateJobRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface JobMapper {

    JobResponse toJobResponse(Job job);

    Job toJob(CreateJobRequest createJobRequest);

    void updateJobFromRequest(UpdateJobRequest updateJobRequest, @MappingTarget Job job);
}