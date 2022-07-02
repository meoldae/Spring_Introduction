package hello.hellospring.domain;

public class Member {
    private Long id;        // 고유번호 
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
