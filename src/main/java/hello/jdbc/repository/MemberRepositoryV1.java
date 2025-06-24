package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
    JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection cnn = null;
        PreparedStatement pstmt = null;

        try {
            cnn = getConnection();
            pstmt = cnn.prepareStatement(sql);
            pstmt.setString(1,member.getMemberid());
            pstmt.setInt(2, member.getMoney());
            int count = pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db err",e);
            throw e;
        }
        finally {
            close(cnn, pstmt, null);
        }

    }

    public Member findById(String memberid) throws SQLException, NoSuchFieldException {
        String sql = "select * from member where member_id = ?";
        Connection cnn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            cnn = getConnection();
            pstmt = cnn.prepareStatement(sql);
            pstmt.setString(1, memberid);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setMemberid(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));

                return member;
            }
            else {
                throw new NoSuchFieldException("member not found memberId="+memberid);
            }
        } catch (SQLException e) {
            log.error("db err",e);
            throw e;
        }
        finally {
            close(cnn, pstmt, rs);
        }

    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection cnn = null;
        PreparedStatement pstmt = null;

        try {
            cnn = getConnection();
            pstmt = cnn.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2,memberId);

            int count = pstmt.executeUpdate();
            log.info("update resultSize={}",count);
        } catch (SQLException e) {
            log.error("db err",e);
            throw e;
        }
        finally {
            close(cnn, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";
        Connection cnn = null;
        PreparedStatement pstmt = null;

        try {
            cnn = getConnection();
            pstmt = cnn.prepareStatement(sql);
            pstmt.setString(1,memberId);

            int count = pstmt.executeUpdate();
            log.info("delete resultSize={}",count);
        }
        catch (SQLException e) {
            log.error("db err",e);
            throw e;
        }
        finally {
            close(cnn, pstmt, null);
        }
    }

    private void close(Connection cnn, PreparedStatement pstmt, ResultSet rs) {
        if(cnn != null){
            try {
                cnn.close();
            } catch (SQLException e) {
                log.error("cnn Close Err",e);
            }
        }

        if(pstmt != null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("pstmt Close Err",e);
            }
        }

        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("rs Close Err",e);
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }


}
