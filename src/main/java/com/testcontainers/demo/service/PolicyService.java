package com.testcontainers.demo.service;

import com.testcontainers.demo.persistence.Policy;
import com.testcontainers.demo.persistence.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository repository;

    public Integer createPolicy(Policy policy) {
        policy.setDateCreated(LocalDateTime.now());
        policy.setLastUpdate(LocalDateTime.now());
        return repository.save(policy).getPolicyId();
    }

    public List<Policy> getAllPolicies() {
        return repository.findAll();
    }

    public Policy getPolicyById(Integer policyId) {
        return repository.findById(policyId).orElse(null);
    }

}
