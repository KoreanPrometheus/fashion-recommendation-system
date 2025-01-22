package com.fastcampus.prometheus.domain.notice.controller;

import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import com.fastcampus.prometheus.domain.notice.service.NoticeService;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
public class NoticeViewController {

    private final NoticeService noticeService;

    // 생성자 주입
    public NoticeViewController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 모든 공지사항 조회
    @GetMapping("/list")
    public String getAllNotices(Model model) {
        List<NoticeResponseDto> noticeList = noticeService.getAllNotices();
        model.addAttribute("noticeList", noticeList);
        return "noticeList";  // "noticeList.jsp"에 공지사항 목록을 전달
    }

    // 특정 공지사항 조회
    @GetMapping("/detail/{id}")
    public String getNoticeById(@PathVariable("id") Long id, Model model) {
        Optional<NoticeResponseDto> noticeResponseDto = noticeService.getNoticeById(id);
        if (noticeResponseDto.isPresent()) {
            model.addAttribute("notice", noticeResponseDto.get());
            return "notice";  // 공지사항 상세 보기 페이지
        } else {
            return "redirect:/notice/list";  // 공지사항을 찾을 수 없는 경우 목록 페이지로 리다이렉트
        }
    }

    // 공지사항 생성 페이지 표시
    @GetMapping("/create")
    public String createNotice(Model model) {
        model.addAttribute("noticeRequestDto", new NoticeRequestDto());
        return "notice";  // 공지사항 작성 폼
    }

    // 공지사항 생성 처리
    @PostMapping
    public String createNotice(@ModelAttribute NoticeRequestDto noticeRequestDto, Model model) {
        NoticeResponseDto createdNotice = noticeService.createNotice(noticeRequestDto);
        model.addAttribute("notice", createdNotice);
        return "redirect:/notice/list";  // 공지사항 목록 페이지로 리다이렉트
    }

    // 공지사항 수정 페이지 표시
    @GetMapping("/edit/{id}")
    public String editNotice(@PathVariable Long id, Model model) {
        Optional<NoticeResponseDto> noticeResponseDto = noticeService.getNoticeById(id);
        if (noticeResponseDto.isPresent()) {
            model.addAttribute("noticeRequestDto", noticeResponseDto.get());
            return "noticeEdit";  // 공지사항 수정 폼
        } else {
            return "redirect:/notice/list";  // 공지사항을 찾을 수 없는 경우 목록 페이지로 리다이렉트
        }
    }

    // 공지사항 수정 처리
    @PostMapping("/edit/{id}")
    public String updateNotice(@PathVariable Long id, @ModelAttribute NoticeRequestDto noticeRequestDto) {
        try {
            noticeService.updateNotice(id, noticeRequestDto);
            return "redirect:/notice/list";  // 공지사항 목록 페이지로 리다이렉트
        } catch (RuntimeException e) {
            return "redirect:/notice/list";  // 공지사항을 찾을 수 없는 경우 목록 페이지로 리다이렉트
        }
    }

    // 공지사항 삭제
    @PostMapping("/delete/{id}")
    public String deleteNotice(@PathVariable Long id) {
        try {
            noticeService.deleteNotice(id);
            return "redirect:/notice/list";  // 공지사항 목록 페이지로 리다이렉트
        } catch (RuntimeException e) {
            return "redirect:/notice/list";  // 공지사항을 찾을 수 없는 경우 목록 페이지로 리다이렉트
        }
    }
}
