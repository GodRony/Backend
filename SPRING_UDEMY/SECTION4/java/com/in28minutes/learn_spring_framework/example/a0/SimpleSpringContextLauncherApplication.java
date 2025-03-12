package com.in28minutes.learn_spring_framework.example.a0;

import com.in28minutes.learn_spring_framework.game.GamingAppLauncherApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan
public class SimpleSpringContextLauncherApplication {
    public static void main(String[] args){
        var  context = new AnnotationConfigApplicationContext(GamingAppLauncherApplication.class);
        // GamingConfiguration -> App03GamingSpringBeans로 변경

        Arrays.stream(context.getBeanDefinitionNames()).
                forEach(System.out::println);

    }
}
