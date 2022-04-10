package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService {

    //DI 방식 이전에 사용했던 소스
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //DI 방식으로 변경 후 사용하는 소스
    private final MemberRepository memberRepository;

    //외부에서 주입하는 방식
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        /*
            같은 이름이 있는 중복 회원X
            member에 값이 있으면(null이 아니라) Exception
            Optional 이기 때문에 가능한 메소드
            Optional 안에 Member 객체가 있는 것
         */
        validateDuplicateMeber(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMeber(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
