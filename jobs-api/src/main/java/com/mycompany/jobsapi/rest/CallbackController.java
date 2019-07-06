package com.mycompany.jobsapi.rest;

import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

  @PostMapping("/debug")
  public Map<String,String> debug(@RequestBody MultiValueMap<String, String> queryMap) {
    return queryMap.toSingleValueMap();
  }

}