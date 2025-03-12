package com.in28minutes.learn_spring_framework.game;

import org.springframework.stereotype.Component;

@Component
public class PacmanGame implements GamingConsole{
    @Override
    public void up() {
        System.out.println("p UP");
    }
    @Override
    public void down() {
        System.out.println("p Down");

    }

    @Override
    public void left() {
        System.out.println("p Left");

    }

    @Override
    public void right() {
        System.out.println("p Right");
    }
}
