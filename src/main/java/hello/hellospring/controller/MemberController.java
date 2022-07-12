package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    /*  스프링 컨테이너에 등록된 멤버 서비스를 스프링이 가져다가 연결시켜 줌 ( DI )
        생성자에 어노테이션을 통해 MemberService를 주입받으므로 생성자 주입 방식

     2. @Autowired private MemberService memberService;
        처럼 필드 자체에 주입 할 수 도 있다.(필드주입방식)
        처음에만 주입되고 나중에 변경시킬 수 없으므로 별로 좋지 않은 방식

     3. @Autowired
        public void setMemberService(MemberService memberService){
            this.memberService = memberService;
        }
        처럼 setter 주입 방식도 존재
        setter는 접근이 public으로 노출되어 문제가 생길 위험이 있음
     */
}