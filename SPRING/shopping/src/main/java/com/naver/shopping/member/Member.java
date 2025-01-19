package com.naver.shopping.member;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
// lombok의 @Getter, @Setter를 쓰면 게터세터 구현안해도됨.
public class Member {
    private Long id;
    private String name;
    private Grade grade;

    public Member(Long id, String name,Grade grade) {
        this.id = id;
        this.grade = grade;
        this.name = name;
    }

/*    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }*/
}
