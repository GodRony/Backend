package com.in28minutes.learn_spring_framework.example.g1;

import com.in28minutes.learn_spring_framework.game.GamingAppLauncherApplication;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component 컴포넌트 대신 Named 사용
@Named
class BusinessServie{
    private DataService dataService;

    public DataService getDataService(){
        return dataService;
    }
    //@Autowired // 왜 세터에 Autowired붙이는겨? Autowired대신 Inject사용
    @Inject
    public  void setDataService(DataService dataService){
        System.out.println("Setter Injection");
        this.dataService = dataService;
    }
}

//@Component
@Named
class DataService {
}


@Configuration
@ComponentScan
public class CdiContextLauncherApplication {
    public static void main(String[] args){
        var  context = new AnnotationConfigApplicationContext(CdiContextLauncherApplication.class);
        // GamingConfiguration -> App03GamingSpringBeans로 변경

        Arrays.stream(context.getBeanDefinitionNames()).
                forEach(System.out::println);
        System.out.println(context.getBean(BusinessServie.class).getDataService());
        // 왜 getter에만 Autowired붙이면 null나오고 setter에 Autowired넣어야 정상적인 값이 나옴?
    }
}
// CDI는 SPRING 어노테이션을 대체한다
// Name은 Component를 대체하고 Inject는 Autowired를 대체한다