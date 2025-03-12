package com.in28minutes.learn_spring_framework.example.d1;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
class ClassA{
}

@Component
@Lazy // Bean을 지연해서 초기화할지 여부를 나타냄 디폴트는 즉시 초기화
class ClassB{
    private ClassA classA;
    public ClassB(ClassA classA){
        //Logic
        System.out.println("Some Initialization logic");
        this.classA = classA;
    }

    public void doSomething(){
        System.out.println("Do Something");
    }
}


@Configuration
@ComponentScan
public class LazyInitializationLauncherApplication {
    public static void main(String[] args){
        var  context = new AnnotationConfigApplicationContext(LazyInitializationLauncherApplication.class);
        // GamingConfiguration -> App03GamingSpringBeans로 변경
        //Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        // 위의 stream으로 빈을 로드하거나 bean에서 메서드를 호출하지 않아도 자동으로 bean이 초기화됨
        // 따라서 Some Initialization logic가 출력이됨.
        // 하지만 이걸 안하게 하고싶으면 Lazy를 하면됨
        // Lazy 사용시 ClassB는 언제 초기화? -> 누군가 ClassB Bean을 사용할때..

        System.out.println("Initialization of context is completed");
        context.getBean(ClassB.class).doSomething();
        // ClassB를 참조하거나 사용하려고 할때만 ClassBean이 로드 되는데 이게 Lazy 어노테이션 기능
    }
}
