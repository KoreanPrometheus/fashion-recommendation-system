package com.fastcampus.prometheus.domain.notice.dto.request;

import com.fastcampus.prometheus.domain.notice.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NoticeRequestDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotNull(message = "내용을 입력하세요.")  // content는 null을 허용하지 않도록 설정
    private String content;

    @NotNull
    private String writer;

    private String noticeType; // Add noticeType

    public Notice toEntity() {
        return Notice.builder()
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .noticeType(this.noticeType)
                .build();
    }
}
