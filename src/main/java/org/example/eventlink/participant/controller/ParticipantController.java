package org.example.eventlink.participant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventlink.participant.dto.ParticipantRequestDto;
import org.example.eventlink.participant.dto.ParticipantResponseDto;
import org.example.eventlink.participant.dto.ParticipantUpdateDto;
import org.example.eventlink.participant.service.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
@Tag(name = "Participant", description = "행사 참여 신청 및 관리 API")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    @Operation(summary = "참여 신청", description = "학생이 행사에 참여 신청합니다.")
    public ParticipantResponseDto createParticipant(@Valid @RequestBody ParticipantRequestDto requestDto) {
        return participantService.createParticipant(requestDto);
    }

    @GetMapping
    @Operation(summary = "참여 신청 목록 조회", description = "등록된 모든 참여 신청 내역을 조회합니다.")
    public List<ParticipantResponseDto> getParticipants() {
        return participantService.getParticipants();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "참여 신청 수정", description = "이름과 학번만 수정합니다.")
    public ParticipantResponseDto updateParticipant(@PathVariable Long id,
                                                    @Valid @RequestBody ParticipantUpdateDto requestDto) {
        return participantService.updateParticipant(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "참여 신청 취소", description = "참여 신청을 취소하고 행사 참여 인원을 감소시킵니다.")
    public void deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
    }
}
