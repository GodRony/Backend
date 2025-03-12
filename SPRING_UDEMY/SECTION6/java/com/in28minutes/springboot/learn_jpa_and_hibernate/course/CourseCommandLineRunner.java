package com.in28minutes.springboot.learn_jpa_and_hibernate.course;

import com.in28minutes.springboot.learn_jpa_and_hibernate.course.jdbc.CourseJdbcRepository;
import com.in28minutes.springboot.learn_jpa_and_hibernate.course.jpa.CourseJpaRepository;
import com.in28minutes.springboot.learn_jpa_and_hibernate.course.springdatajpa.CourseSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Spring이 이 클래스를 자동으로 감지하고 실행하도록 함
public class CourseCommandLineRunner implements CommandLineRunner {
    // CommandLineRunner : 애플리케이션이 실행될 때 자동으로 run() 메서드를 실행하는 인터페이스.
//    @Autowired
//    private CourseJdbcRepository repository;
//    @Autowired
//    private CourseJpaRepository repository;


    @Autowired
    private CourseSpringDataJpaRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new Course(1,"SpringDataJpa 1 ","in28minutes"));
        repository.save(new Course(2,"SpringDataJpa 2","qqqq"));
        repository.save(new Course(3,"SpringDataJpa 3","aaaa"));
        //SpringDataJPA는 insert가 아나라 save임
        //save는 엔터티를 추가하거나 업데이트할때 사용

        repository.deleteById(1L);
        // 프로그램 시작과 동시에 repository.insert(); 호출

        System.out.println(repository.findById(2L));
        System.out.println(repository.findById(3L));

        System.out.println(repository.findAll());
        System.out.println(repository.count());
        System.out.println(repository.findByAuthor("qqqq"));
        System.out.println(repository.findByName("SpringDataJpa 3"));
    }
}
// 애플리케이션 실행시 DB에 데이터 추가
// CommandLineRunner: 애플리케이션 실행 시 특정 코드를 실행하고 싶을 때 사용