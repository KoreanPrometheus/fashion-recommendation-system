package com.fastcampus.prometheus.domain.member.repository;

import com.fastcampus.prometheus.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Notice, Long> {

}