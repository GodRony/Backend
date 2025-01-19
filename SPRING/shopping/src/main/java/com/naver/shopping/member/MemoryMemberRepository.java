package com.naver.shopping.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long,Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}

// 참고로 Bean은 메서드에 붙여서 사용. 컨테이너에 메서드 빈을 개발자가 명시적으로 등록하는거고.
// Component는 클래스에 붙여서 사용. Spring이 클래스 빈을 자동으로 등록해주는건데
// B클래스가 A클래스를 의존하면 Spring이 B를 생성할때 A를 주입함.
// 이때 A클래스, B클래스 모두 컨테이너에 빈으로 등록되고 의존성도 자동으로 설정됨.
// 아무튼 @Component를 사용하면 @Bean을 쓸 필요는 없어.
//