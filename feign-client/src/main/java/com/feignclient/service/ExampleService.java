package com.feignclient.service;

import com.feignclient.client.ExampleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleClient exampleClient;

    public String getDataById(Long id) {
        return exampleClient.getData(id);
    }
}