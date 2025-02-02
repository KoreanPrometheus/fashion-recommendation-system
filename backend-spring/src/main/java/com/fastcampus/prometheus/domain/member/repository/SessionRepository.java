package com.fastcampus.prometheus.domain.member.repository;

import com.fastcampus.prometheus.Session.Session;
import com.fastcampus.prometheus.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByAccessToken(String accessToken);



}
