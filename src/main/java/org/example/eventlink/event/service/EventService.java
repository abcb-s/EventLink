package org.example.eventlink.event.service;

import lombok.RequiredArgsConstructor;
import org.example.eventlink.event.dto.EventRequestDto;
import org.example.eventlink.event.dto.EventResponseDto;
import org.example.eventlink.event.entity.Event;
import org.example.eventlink.event.repository.EventRepository;
import org.example.eventlink.exception.BadRequestException;
import org.example.eventlink.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponseDto createEvent(EventRequestDto dto) {
        validate(dto);

        Event event = Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .eventDate(dto.getEventDate())
                .maxParticipants(dto.getMaxParticipants())
                .currentParticipants(0)
                .createdAt(LocalDateTime.now())
                .build();

        return EventResponseDto.from(eventRepository.save(event));
    }

    public List<EventResponseDto> getEvents() {
        return eventRepository.findAll().stream()
                .map(EventResponseDto::from)
                .toList();
    }

    public EventResponseDto updateEvent(Long id, EventRequestDto dto) {
        Event event = findEventOrThrow(id);
        validate(dto);

        if (dto.getMaxParticipants() < event.getCurrentParticipants()) {
            throw new BadRequestException("현재 참여 인원보다 작게 설정할 수 없습니다.");
        }

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setEventDate(dto.getEventDate());
        event.setMaxParticipants(dto.getMaxParticipants());

        return EventResponseDto.from(eventRepository.save(event));
    }

    public void deleteEvent(Long id) {
        Event event = findEventOrThrow(id);

        if (event.getCurrentParticipants() > 0) {
            throw new BadRequestException("참여 신청자가 존재하여 삭제할 수 없습니다.");
        }

        eventRepository.delete(event);
    }

    public void increaseParticipantCount(Long eventId) {
        Event event = findEventOrThrow(eventId);

        if (event.getCurrentParticipants() >= event.getMaxParticipants()) {
            throw new BadRequestException("신청 가능 인원을 초과했습니다.");
        }

        event.setCurrentParticipants(event.getCurrentParticipants() + 1);
        eventRepository.save(event);
    }

    public void decreaseParticipantCount(Long eventId) {
        Event event = findEventOrThrow(eventId);

        if (event.getCurrentParticipants() > 0) {
            event.setCurrentParticipants(event.getCurrentParticipants() - 1);
        }

        eventRepository.save(event);
    }

    private Event findEventOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("행사를 찾을 수 없습니다."));
    }

    private void validate(EventRequestDto dto) {
        if (!StringUtils.hasText(dto.getTitle())) {
            throw new BadRequestException("제목은 비워둘 수 없습니다.");
        }
        if (!StringUtils.hasText(dto.getDescription())) {
            throw new BadRequestException("설명은 비워둘 수 없습니다.");
        }
        if (!StringUtils.hasText(dto.getLocation())) {
            throw new BadRequestException("장소는 비워둘 수 없습니다.");
        }
        if (dto.getEventDate() == null || dto.getEventDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("행사 날짜는 과거일 수 없습니다.");
        }
        if (dto.getMaxParticipants() == null || dto.getMaxParticipants() <= 0) {
            throw new BadRequestException("최대 참여 인원은 1명 이상이어야 합니다.");
        }
    }
}
