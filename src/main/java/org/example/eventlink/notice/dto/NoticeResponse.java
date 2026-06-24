package org.example.eventlink.notice.dto;

import org.example.eventlink.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static NoticeResponse of(Notice notice){
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedAt()
        );
    }
}
