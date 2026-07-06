package com.feignclient.controller;

import com.feignclient.dto.DataRequest;
import com.feignclient.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign/data")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/{id}")
    public String getData(@PathVariable Long id) {
        return exampleService.getDataById(id);
    }

    @PostMapping
    public String updateData(@RequestParam String name, @RequestParam int value) {
        return exampleService.createData(name, value);
    }

    /*
    @PostMapping
    public String updateData(@RequestBody DataRequest request) {
        return exampleService.createData(request.name(), request.value());
    }
     */

    @PutMapping("/{id}")
    public String updateData(@PathVariable Long id, @RequestParam String name, @RequestParam int value) {
        return exampleService.updateData(id, name, value);
    }

    @DeleteMapping("/{id}")
    public String deleteData(@PathVariable Long id) {
        return exampleService.deleteData(id);
    }
}
