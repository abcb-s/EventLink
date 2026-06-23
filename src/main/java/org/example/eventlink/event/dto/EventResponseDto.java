package org.example.eventlink.event.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.eventlink.event.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class EventResponseDto {

    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDate eventDate;

    private Integer maxParticipants;

    private Integer currentParticipants;

    private LocalDateTime createdAt;

    public static EventResponseDto from(Event event) {
        return EventResponseDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .eventDate(event.getEventDate())
                .maxParticipants(event.getMaxParticipants())
                .currentParticipants(event.getCurrentParticipants())
                .createdAt(event.getCreatedAt())
                .build();
    }
}
