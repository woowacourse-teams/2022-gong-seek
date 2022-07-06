package com.woowacourse.gongseek.member.domain.repository;

import com.woowacourse.gongseek.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByGithubId(String githubId);
}
