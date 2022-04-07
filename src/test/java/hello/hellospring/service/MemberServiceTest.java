package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // DI 전에 사용했던 소스
//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 테스트 전에 실행
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    // 테스트 종료 후 실행
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {

        /*
            hello라는 id로 가입하고 반환된 id를 통해 다시 회원을 찾아서
            new Member객체의 name과 가입된 Member의 name을 비교한다.
         */

        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

/*

        try {
            memberService.join(member2);
            // 예외가 발생하지 않은 것이므로 fail
            fail();
        } catch(IllegalStateException e) {
            // 예외가 발생했으므로 success
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        }

*/
        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}