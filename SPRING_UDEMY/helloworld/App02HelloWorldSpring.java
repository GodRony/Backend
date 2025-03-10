package helloworld;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App02HelloWorldSpring {
    public static void main(String[] args){
        // 1. Launch a Spring Context
        var context =
                new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);

        // 2. Configure the things that we want Spring to manage -@Configuration
        // 3. Retrieving Beans managed by Spring
        System.out.println( context.getBean("name"));
        System.out.println( context.getBean("age"));
        System.out.println( context.getBean("person"));
        System.out.println( context.getBean("person2MethodCall"));
        System.out.println( context.getBean("person3Parameters"));
        // bean으로 설정한 이름으로 가져오거나, 클래스로 갖고올 수 있음
        System.out.println( context.getBean(Person.class));
        System.out.println( context.getBean(Address.class));
        // 하지만 만약에 Person class를 사용하는게 여러개다? 근데 클래스로 가져오겠다? -> 에러발생
        //  Primary로 디폴트를 설정할 수 있으며 해결할 수 있음
        System.out.println( context.getBean("person4Parameters"));
        System.out.println( context.getBean("person5Qualifier"));


        // 내가 사용하는 빈들은 뭔지 출력할 수 있음
        //Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
