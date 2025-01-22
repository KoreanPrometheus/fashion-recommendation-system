package com.fastcampus.prometheus.domain.notice.dto.response;

import com.fastcampus.prometheus.domain.notice.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NoticeResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String noticeType;
    private LocalDateTime regDate; // regDate는 응답에 포함

    public static NoticeResponseDto fromEntity(Notice notice) {
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .noticeType(notice.getNoticeType())
                .regDate(notice.getRegDate())
                .build();
    }
}
