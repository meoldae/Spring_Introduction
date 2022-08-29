package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    /**
     * JPA의 모든 동작은 EntityManager를 통해 이루어지며
     * EntityManager는 Spring에서 자동으로 생성해주기 때문에
     * Injection만 받으면 된다! == 받아야 한다!
     * DataSource 등을 내부적으로 가지고 있어서 알아서 처리한다.
     */

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Find (Class, PK)
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /**
         * Entity 대상으로 Query를 날림
         */
        List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .getResultList();
        return result;
    }
}
