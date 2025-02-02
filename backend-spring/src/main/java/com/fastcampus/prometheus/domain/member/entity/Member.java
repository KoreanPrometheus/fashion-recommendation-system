package com.fastcampus.prometheus.domain.member.entity;


import com.fastcampus.prometheus.Session.Session;
import com.fastcampus.prometheus.domain.member.MemberGender;
import com.fastcampus.prometheus.domain.member.MemberRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberGender memberGender;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private LocalDateTime createdAt;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Session> sessions = new ArrayList<>();

    // 세션 추가
    public Session addSession() {
        Session session = Session.builder()
            .member(this)
            .build();
        sessions.add(session);

        return session;
    }


    @Builder
    public Member(String name, String email, String password, MemberGender memberGender, MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.memberGender = memberGender;
        this.memberRole = memberRole;
        this.createdAt = LocalDateTime.now();
    }

}
