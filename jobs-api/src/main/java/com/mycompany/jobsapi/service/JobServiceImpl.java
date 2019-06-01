package com.mycompany.jobsapi.service;

import java.util.List;

import com.mycompany.jobsapi.exception.JobNotFoundException;
import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.repository.JobRepository;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getNewestJobs(int number) {
        return jobRepository.findAll(PageRequest.of(0, number, Sort.by("createDate").ascending())).getContent();
    }

    @Override
    public Page<Job> getAllJobsByPage(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public Job validateAndGetJobById(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(String.format("Job with id %s not found", id)));
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
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("title", text))
                .should(QueryBuilders.matchPhraseQuery("company", text))
                .should(QueryBuilders.matchPhraseQuery("description", text));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();

        return jobRepository.search(searchQuery);
    }

}
