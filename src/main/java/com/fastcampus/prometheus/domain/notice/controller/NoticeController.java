package com.fastcampus.prometheus.domain.notice.controller;

import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import com.fastcampus.prometheus.domain.notice.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    // 생성자 주입
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 모든 공지사항 조회
    @GetMapping
    public List<NoticeResponseDto> getAllNotices() {
        return noticeService.getAllNotices();
    }

    // 특정 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> getNoticeById(@PathVariable Long id) {
        Optional<NoticeResponseDto> noticeResponseDto = noticeService.getNoticeById(id);
        return noticeResponseDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 공지사항 생성
    @PostMapping
    public ResponseEntity<NoticeResponseDto> createNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
        NoticeResponseDto createdNotice = noticeService.createNotice(noticeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotice);
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto noticeRequestDto) {
        try {
            NoticeResponseDto updatedNotice = noticeService.updateNotice(id, noticeRequestDto);
            return ResponseEntity.ok(updatedNotice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        try {
            noticeService.deleteNotice(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
