package com.mycompany.jobsapi.mapper;

import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.rest.dto.CreateJobDto;
import com.mycompany.jobsapi.rest.dto.JobDto;
import com.mycompany.jobsapi.rest.dto.UpdateJobDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface JobMapper {

    JobDto toJobDto(Job job);

    Job toJob(CreateJobDto createJobDto);

    void updateJobFromDto(UpdateJobDto updateJobDto, @MappingTarget Job job);

}