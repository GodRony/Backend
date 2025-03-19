package com.in28minutes.rest.webservices.restful_web_services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 10)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) 
    // ** ㅡ@ManyToOne(fetch = FetchType.LAZY) 이거 지웠음
    // getPosts()호출시 User가 즉시 로드되지 않아서 Post조회시 User도 함께 가져오지 못함
    @JsonIgnore //** 이거 왜해야함. -> 무한순환 문제 해결
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", description=" + description + "]";
    }




}

