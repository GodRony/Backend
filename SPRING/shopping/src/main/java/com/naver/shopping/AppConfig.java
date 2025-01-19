package com.naver.shopping;

import com.naver.shopping.member.MemberRepository;
import com.naver.shopping.member.MemberService;
import com.naver.shopping.member.MemberServiceImpl;
import com.naver.shopping.member.MemoryMemberRepository;
import com.naver.shopping.order.OrderService;
import com.naver.shopping.order.OrderServiceImpl;
import com.naver.shopping.discount.DiscountPolicy;
import com.naver.shopping.discount.FixDiscountPolicy;
import com.naver.shopping.discount.RateDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
    // 여기에서 discountPolicy 바꾸면 일일히 OrderService를 쓴 클래스를 찾아서 수정하지 않아도됨.
    // 이래서 AppConfig 사용함.

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }


}