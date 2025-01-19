package com.naver.shopping.singleton;

import com.naver.shopping.AppConfig;
import com.naver.shopping.member.MemberRepository;
import com.naver.shopping.member.MemberService;
import com.naver.shopping.member.MemberServiceImpl;
import com.naver.shopping.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();
        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        assertThat(memberService1).isNotSameAs(memberService2);
        // 이 둘이 다르게 나와야 정상.
        // 하지만 나는 클래스 하나당 한 클래스만 쓰고싶지 여러개 쓰고싶지 않을때 singleton사용
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    public void singletonServiceTest() {
        //private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
        //new SingletonService();
        //1. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService1 = SingletonService.getInstance();
        //2. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService2 = SingletonService.getInstance();
        //참조값이 같은 것을 확인
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        assertThat(singletonService1).isSameAs(singletonService2);
        singletonService1.logic();
        singletonService2.logic();
        // 이제 서로 같게나옴.
        // 하지만.. 싱글톤은 단점이 더 많음.
//        싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
//        의존관계상 클라이언트가 구체 클래스에 의존한다. -> DIP를 위반한다.
//        클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
//        테스트하기 어렵다. 내부 속성을 변경하거나 초기화하기 어렵다.
//        private 생성자로 자식 클래스를 만들기 어렵다.
//        결론적으로 유연성이 떨어진다.
//        -> 따라서 spring의 AnnotationConfigApplicationContext를 사용

    }
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //1. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        //2. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        //참조값이 같은 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        assertThat(memberService1).isSameAs(memberService2);
    }

    @Test
    @DisplayName("Configuration 내에서 중복된 new 처리 확인")
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        //모두 같은 인스턴스를 참고하고 있다.
        System.out.println("memberService -> memberRepository = " + memberService.getMemberRepository());
        System.out.println("orderService -> memberRepository = " + orderService.getMemberRepository());
        System.out.println("memberRepository = " + memberRepository);//모두 같은 인스턴스를 참고하고 있다.
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

         // 같은 인스턴스를 참고하는 이유는 싱글톤패턴이라서 그럼
        //  하나의 인스턴스만 생성되며, 해당 인스턴스가 여러 곳에서 공유되니까.
        //memberService, orderService, memberRepository는
        // 모두 같은 memberRepository 인스턴스를 참조하고 있기 때문에
        // memberService -> memberRepository와 orderService -> memberRepository가
        // 동일한 인스턴스를 가리키는 것입니다.
    }

}