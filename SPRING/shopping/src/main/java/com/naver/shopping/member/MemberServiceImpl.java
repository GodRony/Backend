package com.naver.shopping.member;

import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    // 선언은 했는데 왜 초기화하지 않고도 사용할 수 있는지?

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 이건 굳이 왜 필요하지? 생성자 아님?
    // ch5에선 없어두 됐엇는뎅 갑자기 왜 필요해진거?

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}