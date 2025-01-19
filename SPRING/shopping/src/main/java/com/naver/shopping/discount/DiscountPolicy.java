package com.naver.shopping.discount;

import com.naver.shopping.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
