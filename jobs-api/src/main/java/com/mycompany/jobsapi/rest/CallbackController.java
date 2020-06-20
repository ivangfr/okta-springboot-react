package com.mycompany.jobsapi.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CallbackController {

    @Operation(summary = "Endpoint used by Okta to send the access token")
    @PostMapping("/authenticate")
    public Map<String, String> authenticate(@RequestBody MultiValueMap<String, String> queryMap) {
        return queryMap.toSingleValueMap();
    }

}