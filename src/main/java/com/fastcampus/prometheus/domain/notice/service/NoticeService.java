package com.fastcampus.prometheus.domain.notice.service;

import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import com.fastcampus.prometheus.domain.notice.entity.Notice;
import com.fastcampus.prometheus.domain.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 생성자 주입
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 모든 공지사항 조회 (NoticeResponseDto 반환)
    public List<NoticeResponseDto> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(notice -> NoticeResponseDto.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .writer(notice.getWriter())
                        .noticeType(notice.getNoticeType())
                        .regDate(notice.getRegDate())
                        .build())
                .collect(Collectors.toList());
    }

    // 특정 공지사항 조회 (NoticeResponseDto 반환)
    public Optional<NoticeResponseDto> getNoticeById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice.map(n -> NoticeResponseDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .writer(n.getWriter())
                .noticeType(n.getNoticeType())
                .regDate(n.getRegDate())
                .build());
    }

    // 공지사항 생성 (NoticeRequestDto 받기)
    public NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDto) {
        Notice notice = noticeRequestDto.toEntity();  // DTO를 엔티티로 변환
        Notice savedNotice = noticeRepository.save(notice);  // 공지사항 저장

        return NoticeResponseDto.builder()
                .id(savedNotice.getId())
                .title(savedNotice.getTitle())
                .content(savedNotice.getContent())
                .writer(savedNotice.getWriter())
                .noticeType(savedNotice.getNoticeType())
                .regDate(savedNotice.getRegDate())
                .build();
    }

    // 공지사항 수정 (NoticeRequestDto 받기)
    public NoticeResponseDto updateNotice(Long id, NoticeRequestDto noticeRequestDto) {
        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isPresent()) {
            Notice existing = existingNotice.get();
            existing.setTitle(noticeRequestDto.getTitle());  // DTO의 제목으로 수정
            existing.setContent(noticeRequestDto.getContent());  // DTO의 내용으로 수정
            existing.setWriter(noticeRequestDto.getWriter());
            existing.setNoticeType(noticeRequestDto.getNoticeType());
            Notice updatedNotice = noticeRepository.save(existing);  // 수정된 공지사항 저장
            return NoticeResponseDto.builder()
                    .id(updatedNotice.getId())
                    .title(updatedNotice.getTitle())
                    .content(updatedNotice.getContent())
                    .writer(updatedNotice.getWriter())
                    .noticeType(updatedNotice.getNoticeType())
                    .regDate(updatedNotice.getRegDate())
                    .build();
        } else {
            throw new RuntimeException("Notice not found with id " + id);  // 공지사항이 없는 경우
        }
    }

    // 공지사항 삭제
    public void deleteNotice(Long id) {
        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isPresent()) {
            noticeRepository.delete(existingNotice.get());  // 존재하는 공지사항 삭제
        } else {
            throw new RuntimeException("Notice not found with id " + id);  // 공지사항이 없는 경우
        }
    }
}
