package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    /*
    방법 1
    @Autowired
    DataSource dataSource;
    */

    // 방법 2
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

/*
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
*/

/*
    @Bean
    public MemberRepository memberRepository(){
        return new JdbcMemberRepository(dataSource);
    }

*/
    @Bean
    public MemberRepository memberRepository(){
        return new JdbcTemplateMemberRepository(dataSource);
    }
}

/*
Component 어노테이션 없이 직접 코드로 Bean에 추가
Controller는 어차피 스프링이 관리하기때문에, Component Scan과 AutoWired 사용!

이 방식은 구현 클래스 변경이 필요할 때 용이함
기존 작성된 코드의 변경 없이 구현체를 바꿀 수 있음
 */