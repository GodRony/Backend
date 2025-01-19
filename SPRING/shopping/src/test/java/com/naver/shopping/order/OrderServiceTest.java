package com.naver.shopping.order;

import com.naver.shopping.AppConfig;
import com.naver.shopping.member.Grade;
import com.naver.shopping.member.Member;
import com.naver.shopping.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class OrderServiceTest {
    // ch7(맨앞)에서는 아래형식으로 썼음.
    // 근데 주석 해제하면 에러가 뜰거임 생성자때메 ㅇㅇ..
    // 생성자에 필요한 매개변수들이 OrderServiceImpl 클래스의 private 변수로 있음.
    // 이전에 매개변수 안쓰고 썼을땐
    // 생성자 굳이 안만들어도 new로 할당할때 new 클래스이름() 으로 객체생성 가능했음
    // 걍 내가 필요해서.. 매개변수 넣은거
    // 하지만 그렇다면 굳이? OrderServiceImpl 클래스는 왜 private 변수로 매개변수를 썼을까? 메리트는? 이유는?
    // ->
    //OrderService orderService = new OrderServiceImpl();


    // ch7(이후)부터는 AppConfig 쓰면서 내용이 좀 바뀜.
 //   AppConfig appConfig = new AppConfig();
 //   MemberService memberService = appConfig.memberService();
 //   OrderService orderService = appConfig.orderService();

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);
    @Test
    void 주문하기_고정할인() {
        long memberId = 1L;
        Member member = new Member(memberId, "실험체1", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "USB", 24900);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    @Test
    void 주문하기_정률할인() {
        long memberId = 1L;
        Member member = new Member(memberId, "실험체1", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "USB", 24900);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(2490);
    }

    // ch7(맨앞) 이전 코드
//    class OrderServiceTest {
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();
//
//        @Test
//        void 주문하기() {
//            long memberId = 1L;
//            Member member = new Member(memberId, "실험체1", Grade.VIP);
//            memberService.join(member);
//            Order order = orderService.createOrder(memberId, "USB", 24900);
//            Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
//        }
//    }
}