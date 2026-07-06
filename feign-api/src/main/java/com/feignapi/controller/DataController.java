package com.feignapi.controller;

import com.feignapi.dto.DataRequest;
import com.feignapi.dto.DataResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data") // /api/data를 붙이고 들어와야 함 반복되는 URL
public class DataController {
    private final Map<Long, DataResponse> dataStore = new HashMap<>(); //데이터 베이스 역할
    private long idCounter = 1L;

    @PostConstruct
    public void initDataStore() {
        for (long i = 1; i <= 5; i++) {
            dataStore.put(idCounter++,
                    DataResponse.builder()
                            .id(i)
                            .name("item" + i)
                            .value((int)(100 * i))
                            .build());
        }
    }

    //ex) api/data/1 <- 동적으로 들어와야 함
    @GetMapping("/{id}")
    public DataResponse getData(@PathVariable Long id) {
        var response = dataStore.get(id);
        if (response == null ) throw new RuntimeException("Data not found(get) " + id);
        return response;
    }

    @PostMapping
    public DataResponse createData(@RequestBody DataRequest dataRequest) {
        var dataResponse = DataResponse.builder()
                .id(idCounter++)
                .name(dataRequest.name())
                .value(dataRequest.value())
                .build();
        dataStore.put(dataResponse.id(), dataResponse);
        return dataResponse;
    }

    @PutMapping("/{id}")
    public DataResponse updateData(
            @PathVariable("id") Long id,
            @RequestBody DataRequest dataRequest
    ) {
        DataResponse response = dataStore.get(id);
        if (response == null) throw new RuntimeException("Data not found(update) " + id);

        var newResponse = DataResponse.builder()
                        .id(id)
                        .name(dataRequest.name())
                        .value(dataRequest.value())
                        .build();
        dataStore.put(id, newResponse);
        return newResponse;
    }

    @DeleteMapping("/{id}")
    public String deleteData(@PathVariable Long id) {
        var response = dataStore.remove(id);
        if (response == null) throw new RuntimeException("Data not found(delete) " + id);
        return "Data deleted with id: " + id;
    }
}
