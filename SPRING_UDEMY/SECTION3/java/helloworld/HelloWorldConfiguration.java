package helloworld;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

record Address(String firstLine,String city){
}
record Person(String name,int age,Address address){
};
@Configuration
public class HelloWorldConfiguration {
    @Bean
    public String name(){
        return "Ranga";
    }
    @Bean
    public int age(){
        return 15;
    }
    @Primary
    @Bean(name = "address2")
    public Address address(){
        return new Address("HEEYOUNG","Seoul");
    }
    @Qualifier("address3Qualifier")
    @Bean(name = "address3")
    public Address address3(){
        return new Address("QWER","Busan");
    }
    @Primary
    @Bean
    public Person person(){
        return new Person("Ravi",20,new Address("HEEYOUNG","Seoul"));
    }
    @Bean
    public Person person2MethodCall(){
        return  new Person(name(),age(),address());
    }
    @Bean
    public Person person3Parameters(String name,int age, Address address3){
        return  new Person(name,age,address3);
    }
    // 기존 bean address3을 이용해서 새로운 bean을 만들어볼 수 있다.
    @Bean
    public Person person4Parameters(String name,int age, Address address){
        return  new Person(name,age,address);
    }
    // address2,3은 있지만 address는 없기때문에 이경우에도 오류발생 -> 둘 중 하나에 primary를 줘서 해결

    @Bean
    public Person person5Qualifier(String name,int age,@Qualifier("address3Qualifier") Address address){
        return  new Person(name,age,address);
    }
    // primary로 address2를 쓰고싶지 않을때.. address3쓰고싶을땐 Qualifier
}
