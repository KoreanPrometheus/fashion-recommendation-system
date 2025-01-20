package com.fastcampus.prometheus.domain.member.repository;

import com.fastcampus.prometheus.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findMemberById(Long id);
}
