package com.testcontainers.demo.web;

import com.testcontainers.demo.persistence.Policy;
import com.testcontainers.demo.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping (value = "/api/policy")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping
    private ResponseEntity<Integer> createPolicy(@RequestBody Policy policy) {
        return new ResponseEntity<>(policyService.createPolicy(policy), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Policy>> getAllPolicies() {
        return new ResponseEntity<>(policyService.getAllPolicies(), HttpStatus.OK);
    }

    @GetMapping("/{policyId}")
    private ResponseEntity<Policy> getPolicyById(@PathVariable Integer policyId) {
        Policy policy = policyService.getPolicyById(policyId);
        return new ResponseEntity<>(policy, policy != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
