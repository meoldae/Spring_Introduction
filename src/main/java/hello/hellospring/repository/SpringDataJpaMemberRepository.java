package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{

    // 구현 필요 없음!
    // 스프링 데이터 JPA가 구현체를 자동으로 생성하고, 등록함
    @Override
    Optional<Member> findByName(String name);

    /**
     * findById가 아니라 findByName일 경우 데이터 JPA에서 일어나는 일
     * select m from Member m where m.name = ?
     * 알아서(규칙) Query 작성
     * 즉, Interface 이름만으로도 개발이 가능 !!
     */
}
