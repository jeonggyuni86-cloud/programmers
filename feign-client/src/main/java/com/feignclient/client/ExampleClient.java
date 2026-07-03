package com.feignclient.client;

// * Feign Client 선언부
// - interface 라서 우리가 직접 구현하지 않는다.
// 즉, "다른 서버로 HTTP 요청을 보내는 코드"를 인터페이스 선언만으로 대신 만들어주는 것

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// * @FeignClient
// - name: 이 클ㄹ라이언트의 고유 이름(Bean 이름), 필수값
// - URL: 호출할 대상 서버 주소. ${feign-api.url}" 은 application.yaml 에서 값을 읽어온다.
// 즉, 주소를 하드코딩 하지 않고 설정으로 분리

@FeignClient(name = "exampleClient", url = "${feign-api.url}/api/data")
public interface ExampleClient {

    @GetMapping("/{id}")
    String getData(@PathVariable Long id);

}
