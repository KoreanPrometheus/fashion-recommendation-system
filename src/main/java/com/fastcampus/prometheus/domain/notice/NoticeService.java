package com.fastcampus.prometheus.domain.notice;

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

    // 모든 공지사항 조회 (NoticeDto 반환)
    public List<NoticeDto> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(notice -> new NoticeDto(
                        notice.getTitle(),
                        notice.getContent(),
                        notice.getWriter())
                ).collect(Collectors.toList()
                );
    }

    // 특정 공지사항 조회 (NoticeDto 반환)
    public Optional<NoticeDto> getNoticeById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice.map(n -> new NoticeDto(
                n.getTitle(),
                n.getWriter(),
                n.getContent())
        );
    }

    // 공지사항 생성 (NoticeDto 받기)
    public NoticeDto createNotice(NoticeDto noticeDto) {
        Notice notice = noticeDto.toEntity();  // DTO를 엔티티로 변환
        Notice savedNotice = noticeRepository.save(notice);  // 공지사항 저장

        return new NoticeDto(
                savedNotice.getTitle(),
                notice.getWriter(),
                savedNotice.getContent()
        );
    }

    // 공지사항 수정 (NoticeDto 받기)
    public NoticeDto updateNotice(Long id, NoticeDto noticeDto) {
        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isPresent()) {
            Notice existing = existingNotice.get();
            existing.setTitle(noticeDto.getTitle());  // DTO의 제목으로 수정
            existing.setContent(noticeDto.getContent());  // DTO의 내용으로 수정
            existing.setWriter(noticeDto.getWriter());
            Notice updatedNotice = noticeRepository.save(existing);  // 수정된 공지사항 저장
            return new NoticeDto(
                    updatedNotice.getTitle(),
                    updatedNotice.getWriter(),
                    updatedNotice.getContent()
            );
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
