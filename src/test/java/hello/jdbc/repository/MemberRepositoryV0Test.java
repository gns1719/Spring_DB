package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException, NoSuchFieldException {
        //save
        Member member = new Member("memberV2", 10000);
        repository.save(member);
    
        //findById
        Member findMember = repository.findById(member.getMemberid());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);
    }
}