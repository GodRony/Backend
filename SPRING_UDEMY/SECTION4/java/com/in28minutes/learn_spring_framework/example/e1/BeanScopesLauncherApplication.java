package com.in28minutes.learn_spring_framework.example.e1;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class  NormalClass{ // 생성하기만 하면 같은 인스턴스를 사용함. (싱글톤)
    // 싱글톤은 Spring IoC 컨테이너 당 객체 인스턴스 딱 하나, 디폴트가 싱글톤

}
@Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component // Bean을 참조할때마다 매번 다른 인스턴스를 생성하고싶으면 Prototype사용
class PrototypeClass{

}
@Configuration
@ComponentScan
public class BeanScopesLauncherApplication {
    public static void main(String[] args){
        var context = new AnnotationConfigApplicationContext(BeanScopesLauncherApplication.class);
  //      Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        System.out.println(context.getBean(NormalClass.class)); // 오직 하나의 인스턴스
        System.out.println(context.getBean(NormalClass.class));
        System.out.println(context.getBean(NormalClass.class));
        System.out.println(context.getBean(PrototypeClass.class)); // 여러 인스턴스 생성
        System.out.println(context.getBean(PrototypeClass.class));
        System.out.println(context.getBean(PrototypeClass.class));
    }
}
