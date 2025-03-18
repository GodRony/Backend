package com.in28minutes.rest.webservices.restful_web_services.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// REST API 버전 관리하기
@RestController
public class VersioningPersonController {
    // 1. URI 방식
    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson(){
        return new PersonV1("Bob Charlie");
    }
    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson(){
        return new PersonV2(new Name("Bob","Chalie"));
    }

    // 2. Request parameter 방식
    // /person?version=1
    // 요청 param이 있을때만 얘를 호출하고싶을땐 path와 params를 붙여
    @GetMapping(path = "/person",params="version=1")
    public PersonV1 getFirstVersionOfPersonRequestParameter(){
        return new PersonV1("Bob Charlie");
    }
    @GetMapping(path = "/person",params="version=2")
    public PersonV2 getSecondVersionOfPersonRequestParameter(){
        return new PersonV2(new Name("Bob","Chalie"));
    }

    // 2. headers versioning 방식
    @GetMapping(path = "/person/header",headers="X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonRequestHeader(){
        return new PersonV1("Bob Charlie");
    }
    @GetMapping(path = "/person/header",headers="X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonRequestHeader(){
        return new PersonV2(new Name("Bob","Chalie"));
    }

    // 2. Media type versioning 방식
    @GetMapping(path = "/person/accept",produces="application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeader(){
        return new PersonV1("Bob Charlie");
    }
    @GetMapping(path = "/person/accept",produces="application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonAcceptHeader(){
        return new PersonV2(new Name("Bob","Chalie"));
    }
}
