package com.in28minutes.springboot.learn_spring_boot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CourseController {


    // 누군가 /course를 입력하면 이 코스 목록을 반환할거임 (json으로)
    @RequestMapping("/courses")
    public List<Course> retrieveAllCourses(){
        return Arrays.asList(
                new Course(1,"Learn AWS","in28minutes"),
                new Course(1,"Learn DevOps ","in28minutes")
        );
    }

}
