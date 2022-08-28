package com.ivanfranchin.jobsapi.mapper;

import com.ivanfranchin.jobsapi.model.Job;
import com.ivanfranchin.jobsapi.rest.dto.CreateJobRequest;
import com.ivanfranchin.jobsapi.rest.dto.JobResponse;
import com.ivanfranchin.jobsapi.rest.dto.UpdateJobRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface JobMapper {

    JobResponse toJobResponse(Job job);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    Job toJob(CreateJobRequest createJobRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    void updateJobFromRequest(UpdateJobRequest updateJobRequest, @MappingTarget Job job);
}