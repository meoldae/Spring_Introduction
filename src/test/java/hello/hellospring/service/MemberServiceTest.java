package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() {
        //Given
        Member member = new Member();
        member.setName("meoldae");

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

// Try - catch 이용
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e){
//            정상 결과
//            Assertions.assertThat(e.getMessage()).isEqualTo("중복된 회원이 있습니다.");
//            Assertions.assertThat(e.getMessage()).isEqualTo("중복된 회원이 있습니다. 실패");
//        }

        //Then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}