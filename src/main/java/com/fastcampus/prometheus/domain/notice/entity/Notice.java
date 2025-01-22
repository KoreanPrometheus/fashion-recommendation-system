package com.fastcampus.prometheus.domain.notice.entity;

import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "notice")
public class Notice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(name = "notice_type")
    private String noticeType; // Add noticeType to the entity

    @Column(name = "reg_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime regDate; // Add regDate (created date)

    // Convert from NoticeRequestDto to Notice entity
    public static Notice fromRequestDto(NoticeRequestDto noticeRequestDto) {
        return Notice.builder()
                .title(noticeRequestDto.getTitle())
                .content(noticeRequestDto.getContent())
                .writer(noticeRequestDto.getWriter())
                .noticeType(noticeRequestDto.getNoticeType())
                .build();
    }

    // Convert from Notice entity to NoticeResponseDto
    public NoticeResponseDto toResponseDto() {
        return new NoticeResponseDto(
                this.id,
                this.title,
                this.content,
                this.writer,
                this.noticeType,
                this.regDate
        );
    }
}
