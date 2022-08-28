package com.ivanfranchin.jobsapi.rest.dto;

public record JobResponse(String id, String title, String company, String logoUrl, String description,
                          String createDate) {
}
