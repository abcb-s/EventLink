package org.example.eventlink.notice.service;

import lombok.RequiredArgsConstructor;
import org.example.eventlink.notice.dto.DeleteResponse;
import org.example.eventlink.notice.dto.NoticeRequest;
import org.example.eventlink.notice.dto.NoticeResponse;
import org.example.eventlink.notice.entity.Notice;
import org.example.eventlink.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeResponse createNotice(NoticeRequest request) {

        if (request.title() == null) {
            throw new IllegalArgumentException("title은 필수항목입니다.");
        }

        if (request.content() == null) {
            throw new IllegalArgumentException("content는 필수항목입니다.");
        }

        Notice notice = Notice.builder()
                .title(request.title())
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);

        return NoticeResponse.of(notice);
    }

    public List<NoticeResponse> getNotices(){

        return noticeRepository.findAll()
                .stream()
                .map(NoticeResponse::of)
                .toList();
    }

    public NoticeResponse updateNotice(Long id, NoticeRequest request){

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 id입니다.")
                );

        notice.setTitle(request.title());
        notice.setContent(request.content());

        noticeRepository.save(notice);

        return NoticeResponse.of(notice);
    }

    public DeleteResponse deleteNotice(Long id){

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 id입니다.")
                );

        noticeRepository.deleteById(id);

        return DeleteResponse.of("공지사항이 삭제되었습니다.");
    }
}
