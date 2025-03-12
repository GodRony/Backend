package com.in28minutes.learn_spring_framework.game;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MarioGame implements GamingConsole{
    public void up(){
        System.out.println("m Jump");
    }
    public void down(){
        System.out.println("m Go into a hole");
    }
    public void left(){
        System.out.println("m Go back");
    }
    public void right(){
        System.out.println("m Accelerate");
    }
}
