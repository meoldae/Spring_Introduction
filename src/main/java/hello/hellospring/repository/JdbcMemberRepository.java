package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        // 결과
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // RETURN_GENERATED_KEYS : DB에 Insert를 해야 ID값을 얻을 수 있었는데 그 때 쓰이는 변수

            // Index 1은 Query 의 첫 번째 ? 위치에 member.getName()이 맵핑됨
            pstmt.setString(1, member.getName());

            // 이 때 DB에 Query가 전송됨
           pstmt.executeUpdate();
            // DB가 생성한 Key(ID)를 반환해줌
            rs = pstmt.getGeneratedKeys();

            // 값을 가지고 있으면?
            if (rs.next()) {
                // 값을 꺼내서 Member 객체에 ID 세팅
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            // 사용한 리소스들 해제시켜야 함
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            // 조회는 Update가 아니고 Query
            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
        /*
        Spring 사용시 DataSourceUtils를 통해서 Connection 획득을 해야한다.
        그래야 DataConnection이 유지되고 이전의 트랜잭션을 처리할 수 있다.
         */
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // 파라미터를 역순으로
        try{
            if(rs != null){
                rs.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(pstmt != null){
                pstmt.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(conn != null){
                // Connection 은 닫을 때에도 DataSourceUtils를 통해 닫아야 한다.
                close(conn);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
