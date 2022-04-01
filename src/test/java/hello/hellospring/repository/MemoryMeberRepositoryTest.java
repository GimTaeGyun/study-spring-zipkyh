package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// public 안해도 됩니다. 따른데서 갖다 쓸게 아니니까요.
class MemoryMeberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /*
        테스트 순서는 랜덤. 서로 의존적으로 설계하면 안 됩니다.
        테스트가 끝나면 repository 지워주는 코드 넣어주죠.
        테스트가 실행되고 끝날 때마다 저장소를 다 지워요.
        그러면 순서와 상관이 없어지겠죠. 중요한 내용입니다.
        (흠 근데 순서 정하는거 나는 해봤는데.. 나중에 찾아보자.)
     */
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        /*
            [ 마지막 검증 ]
            Optional에서 값을 꺼낼 때 get으로 꺼낼 수 있다.
            좋은 방법은 아니지만 테스트 코드니까 상관 없다.
         */
        Member result = repository.findById(member.getId()).get();

        /*
            [ 검증 방법 ]
            new에서 꺼낸 것과 db에서 꺼낸 것이 똑같으면 참
         */

        // 1) print해보는 방법
        System.out.println("result = " + (result == member));

        // 2) Assertions (junit.jupiter)
        // 앞 : expected, 뒤 : actual
//        Assertions.assertEquals(member, null);

        // 3) Assertions (assertj.core)
        // 2) 보다 좀 더 편하다.
        // static import로 하면 바로 함수 사용 가능
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
