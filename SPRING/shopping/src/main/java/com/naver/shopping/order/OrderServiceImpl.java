package com.naver.shopping.order;

import com.naver.shopping.member.Member;
import com.naver.shopping.member.MemberRepository;
import com.naver.shopping.discount.DiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// 롬복을 사용하면(@RequiredArgsConstructor) 생성자를 제거해야함.
// 왜냐면 알아서 생성자를 만들어주기 때문.
public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    // 이것두 MemberServiceImpl처럼 new 로 할당 안했넹.

    // ########## 1. 이전 방식 ###############
    //    1) Impl에서는 new로 선언과 함께 할당해줘야했고,
    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //    2) OrderApp에서 이것을 사용할때에는
    //    OrderService orderService = new OrderServiceImpl(); 이런식으로 new를 또 해줘야했음.
    //    생성자 생기고 new로 할당안함.
    // ########## 2. 이후 방식 ###############
    //    1) Impl에서는 선언만 하고 할당해주지 않아도 됨.
    //
     // 언제 생겼냐면, ch7에서 AppConfig 만들고 나서 생김.


//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }


    // 이건 굳이 왜 필요하지? 생성자 아님?
    // ch5에선 없어두 됐엇는뎅 갑자기 왜 필요해진거?
    // 언제 생겼냐면, ch7에서 AppConfig 만들고 나서 생김.

    // 자동으로 설정할 Autowired 같은 경우에는 아예 생성자를 작성한 곳으로 타고 감

    // ############ Autowired 옵션설정 #############
    // 1. @Autowired(required = false) :  생성자 자체가 없던 일이 됨.
    // 2. null로 넘어가기
//    @Autowired
//    public OrderServiceImpl(@Nullable  MemberRepository memberRepository, @Nullable DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
    // 3. Optional을 써서 넘겨주기
//    @Autowired
//    public OrderServiceImpl(Optional<MemberRepository> memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository.get();
//        this.discountPolicy = discountPolicy;
//    }
    // memberRepository는 해당하는 타입의 빈을 찾지 못하면 Optional.empty라는 값을 반환해 넘어감.
    // 하지만 만 Nullable, Optional과 같은 방식을 쓰지 않은 discountPolicy를 찾지 못하면 얄짤없이 에러가 남.


    // ############ 조회된 빈이 2개이상인 경우 ############
    // 위의 public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) 는
    // DiscountPolicy가 인터페이스로 구현된 discount 함수를 쓰는 클래스가 FixDiscountPolicy, RateDiscountPolicy로 두개임.
    // 그러면 우리는 저 생성자의 discountPolicy에 어떤 할인정책 클래스가 들어가게 되는지 모름.
    // 1. 파라미터 이름을 원하는 빈의 이름으로 (의존도가 올라감.. bad)
    // @Autowired
    // public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy)
    // 2. @Primary를 사용하자
    // FixDiscountPolicy랑 RateDiscountPolicy에 가서 @primary 붙이기.
    // RateDiscountPolicy에 붙여주면 얘가 우선적으로 사용.
    // 3. @Qualifier를 사용하자
    // @Primary가 아닌 다른 특정한 빈을 사용하고 싶은 경우에 쓰임.
    // FixDiscountPolicy에 붙여주자. 이때 별칭을 줄 수 있음.

    /*@Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("subDiscountPolicy") DiscountPolicy rateDiscountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = rateDiscountPolicy;
    }*/

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}