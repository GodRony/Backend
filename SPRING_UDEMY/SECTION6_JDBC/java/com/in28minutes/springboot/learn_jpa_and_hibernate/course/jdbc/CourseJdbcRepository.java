package com.in28minutes.springboot.learn_jpa_and_hibernate.course.jdbc;

import com.in28minutes.springboot.learn_jpa_and_hibernate.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository //@Repository : DB와 관련된 작업을 처리하는 클래스임을 나타냄
public class CourseJdbcRepository {  // DB에 데이터를 추가하는 역할

    // @Autowired 스프링이 자동으로 객체(Bean)를 주입해줘야함
    // 만약 없이한다면, new JdbcTemplate(); 직접 객체 생성해야함 Spring이 관리하는 데이터베이스 연결 정보를 못 가져옴
    // 참고로 @Autowired로 같은 타입(JdbcTemplate)을 여러 개 선언하면
    // Spring이 어떤 Bean을 주입해야 할지 몰라서 에러가 발생!
    @Autowired
    private JdbcTemplate springJdbcTemplate;
    // JdbcTemplate : SQL을 쉽게 실행할 수 있도록 도와주는 Spring의 도구.
    // 참고로 JdbcTemplate 객체가 여러개라는건.. 여러개의 db를 사용하겠단 의미로 해석가능
    private static String INSERT_QUERY =
            """
                    insert into course(id,name,author)
                    values(?,?,?);
            """;
    private static String DELETE_QUERY =
            """
                    delete from course where id = ?;
            """;
    private static String SELECT_QUERY =
            """
                    select * from course where id = ?;
            """;

    public void insert(Course course){
        springJdbcTemplate.update(INSERT_QUERY,
                course.getId(),course.getName(),course.getAuthor());
        // springJdbcTemplate.update로 쿼리 실행 (SQL 실행)
    }
    public void deleteById(long id){
        springJdbcTemplate.update(DELETE_QUERY,id);
    }
    public Course findById(long id){
        return springJdbcTemplate.queryForObject(SELECT_QUERY,
                new BeanPropertyRowMapper<>(Course.class),id);
        // ResultSet를 가져와서 Bean 에 매핑하고 id도 입력값으로 보냄
        // ResultSet -> Bean -> Row Mapper
    }
}
//  JDBC: 직접 SQL을 실행해서 데이터베이스를 다룸.