package com.fastcampus.prometheus.domain.notice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotNull(message = "내용을 입력하세요.")  // content는 null을 허용하지 않도록 설정
    private String content;

    @NotNull
    private String writer;

//    @Builder.Default
//    private String noticeType;
//
//    @NotNull
//    private boolean isDeleted;

    public Notice toEntity() {
        return Notice.builder()
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
//                .noticeType(this.noticeType)
                .build();
    }
}
