package com.in28minutes.learn_spring_framework.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("SuperContraGameQualifier")
public class SuperContraGame implements GamingConsole{
    public void up(){
        System.out.println("s up");
    }
    public void down(){
        System.out.println("s Sit down");
    }
    public void left(){
        System.out.println("s Go back");
    }
    public void right(){
        System.out.println("s Shoot a bullet");
    }
}
