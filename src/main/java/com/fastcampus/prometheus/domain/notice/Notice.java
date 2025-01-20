package com.fastcampus.prometheus.domain.notice;

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
    String writer;

//    @Column(name = "cnt", columnDefinition = "integer default 0")
//    private long viewCnt;
//
//    @Column(name = "notice_type", nullable = false)
//    private String noticeType;
//
//    @Column(name = "reg_date", nullable = false)
//    @CreationTimestamp
//    private LocalDateTime regDate;
//
//    @Column(name = "deleted_date")
//    private LocalDateTime deletedDate;
//
//    @Column
//    private Boolean isFixed = false;
//
//    @Column
//    private Boolean isDeleted = false;
//
//    public void incrementViewCnt(){this.viewCnt++;}
//
//    public void markAsDeleted() {
//        this.isDeleted = true;
//        this.deletedDate = LocalDateTime.now();
//    }

}
