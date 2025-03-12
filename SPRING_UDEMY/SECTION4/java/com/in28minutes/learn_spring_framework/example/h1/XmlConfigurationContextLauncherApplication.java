package com.in28minutes.learn_spring_framework.example.h1;


import com.in28minutes.learn_spring_framework.game.GameRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class XmlConfigurationContextLauncherApplication {
    public static void main(String[] args){
        var  context = new ClassPathXmlApplicationContext("contextConfiguration.xml");
        // GamingConfiguration -> App03GamingSpringBeans로 변경

        Arrays.stream(context.getBeanDefinitionNames()).
                forEach(System.out::println);

        System.out.println(context.getBean("name"));
        System.out.println(context.getBean("age"));
        context.getBean(GameRunner.class).run();

    }
}
// 이전에는 Xml로 의존성을 주입했지만 이젠 거의 사용하지 않고 Spring의 에노테이션을 사용한다
