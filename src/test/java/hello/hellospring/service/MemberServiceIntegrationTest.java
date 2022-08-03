package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
// 진짜 Spring을 가동시켜서 테스트를 실행
@Transactional
// Transactional 주석이 없으면 테스트 이후 DB에 값 저장
// 테스트 시작 전 트랜잭션을 시작하고 테스트 종료 후 RollBack 실행
// @Commit 어노테이션을 통해 반영시킬 수 도 있음
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //Given
        Member member = new Member();
        member.setName("Transactional Test");

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복회원_예외(){
        //Given
        Member member1 = new Member();
        member1.setName("meoldae1");

        Member member2 = new Member();
        member2.setName("meoldae1");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("중복된 회원이 있습니다.");

    }
}