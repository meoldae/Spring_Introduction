package hello.hellospring.domain;

import javax.persistence.*;

// JPA가 관리하는 엔티티가 됨
@Entity
public class Member {

    // Primary Key
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 고유번호

    //@Column(name ="username")
    // DB와 Mapping
    private String name;    // 회원 이름

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
