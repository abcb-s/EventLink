package org.example.eventlink.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.eventlink.event.dto.EventRequestDto;
import org.example.eventlink.event.dto.EventResponseDto;
import org.example.eventlink.event.service.EventService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Event", description = "학교 행사 생성 및 관리 API")
public class EventController {

    private final EventService eventService;

    @PostMapping
    @Operation(summary = "행사 생성", description = "새로운 학교 행사를 생성합니다.")
    public EventResponseDto createEvent(@RequestBody EventRequestDto requestDto) {
        return eventService.createEvent(requestDto);
    }

    @GetMapping
    @Operation(summary = "행사 목록 조회", description = "등록된 모든 학교 행사를 조회합니다.")
    public List<EventResponseDto> getEvents() {
        return eventService.getEvents();
    }

    @PutMapping("/{id}")
    @Operation(summary = "행사 수정", description = "기존 학교 행사 정보를 수정합니다.")
    public EventResponseDto updateEvent(@PathVariable Long id, @RequestBody EventRequestDto requestDto) {
        return eventService.updateEvent(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "행사 삭제", description = "참여 신청자가 없는 학교 행사를 삭제합니다.")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
