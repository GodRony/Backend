package com.in28minutes.learn_spring_framework.example.a1;

import com.in28minutes.learn_spring_framework.game.GamingAppLauncherApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
class YourBusinessClass{
    Dependency1 dependency1;

    Dependency2 dependency2;
    @Autowired
    // Autowired를 안해도 스프링이 자동으로 생성자를 사용해서 객체를 만듬
    public YourBusinessClass(Dependency1 dependency1, Dependency2 dependency2) {
       System.out.println("Constructor Injection - YourBusinessClass");

        this.dependency1 = dependency1;
        this.dependency2 = dependency2;
    }
// setter dependency
//    @Autowired
//    public void setDependency1(Dependency1 dependency1) {
//        System.out.println("Setter Injection - setDependency1 ");
//        this.dependency1 = dependency1;
//    }
//
//    @Autowired
//    public void setDependency2(Dependency2 dependency2) {
//        System.out.println("Setter Injection - setDependency2 ");
//        this.dependency2 = dependency2;
//    }



    public String toString(){
        return "Using " + dependency1 + " and " + dependency2;
    }
}
@Component
class Dependency1{

}
@Component
class Dependency2{

}

@Configuration
@ComponentScan
// 만약에 ComponentScan에서 별도로 패키지를 정하지 않으면 현재 패키지를 스캔함
public class DepInjectionLauncherApplication {
    public static void main(String[] args){
        var  context = new AnnotationConfigApplicationContext(DepInjectionLauncherApplication.class);
        // GamingConfiguration -> App03GamingSpringBeans로 변경

        Arrays.stream(context.getBeanDefinitionNames()).
                forEach(System.out::println);
        System.out.println(context.getBean(YourBusinessClass.class));
        // 이렇게 하면 Using null and null 뜨는데 이유는 스프링 프렘워크가 의존성을 자동 와이어링 안해서그럼
    }
}

// 의존성 주입 유형
// 1. 생성자 주입
// autowired 안해도 자동으로 스프링이 의존성을 만듬
// 2. setter-based 주입
// 당신의빈에 있는 setter 메소드를 사용할때 의존성이 주입됨.
// 3. 필드

// 생성자 주입 방식을 추천함. 모든 초기화가 하나의 메소드에서 발생하니까..
// 초기화가 완료되면 Bean을 사용할 준비가 된것임.

