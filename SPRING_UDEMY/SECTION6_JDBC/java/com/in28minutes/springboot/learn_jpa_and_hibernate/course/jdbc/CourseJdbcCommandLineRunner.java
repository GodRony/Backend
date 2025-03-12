package com.in28minutes.springboot.learn_jpa_and_hibernate.course.jdbc;

import com.in28minutes.springboot.learn_jpa_and_hibernate.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Spring이 이 클래스를 자동으로 감지하고 실행하도록 함
public class CourseJdbcCommandLineRunner implements CommandLineRunner {
    // CommandLineRunner : 애플리케이션이 실행될 때 자동으로 run() 메서드를 실행하는 인터페이스.
    @Autowired
    private CourseJdbcRepository repository;
    @Override
    public void run(String... args) throws Exception {
        repository.insert(new Course(1,"Learn AWS now! ","in28minutes"));
        repository.insert(new Course(2,"qwer! ","qqqq"));
        repository.insert(new Course(3,"asdf! ","aaaa"));

        repository.deleteById(1);
        // 프로그램 시작과 동시에 repository.insert(); 호출

        System.out.println(repository.findById(2));
        System.out.println(repository.findById(3));
    }
}
// 애플리케이션 실행시 DB에 데이터 추가
// CommandLineRunner: 애플리케이션 실행 시 특정 코드를 실행하고 싶을 때 사용