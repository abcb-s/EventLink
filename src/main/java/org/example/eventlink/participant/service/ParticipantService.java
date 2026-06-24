package org.example.eventlink.participant.service;

import lombok.RequiredArgsConstructor;
import org.example.eventlink.event.service.EventService;
import org.example.eventlink.exception.BadRequestException;
import org.example.eventlink.exception.ResourceNotFoundException;
import org.example.eventlink.participant.dto.ParticipantRequestDto;
import org.example.eventlink.participant.dto.ParticipantResponseDto;
import org.example.eventlink.participant.dto.ParticipantUpdateDto;
import org.example.eventlink.participant.entity.Participant;
import org.example.eventlink.participant.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EventService eventService;

    @Transactional
    public ParticipantResponseDto createParticipant(ParticipantRequestDto dto) {
        validateDuplicateParticipant(dto.getStudentNumber(), dto.getEventId());

        Participant participant = Participant.builder()
                .name(dto.getName())
                .studentNumber(dto.getStudentNumber())
                .eventId(dto.getEventId())
                .appliedAt(LocalDateTime.now())
                .build();

        Participant savedParticipant = participantRepository.save(participant);
        eventService.increaseParticipantCount(dto.getEventId());

        return ParticipantResponseDto.from(savedParticipant);
    }

    public List<ParticipantResponseDto> getParticipants() {
        return participantRepository.findAll().stream()
                .map(ParticipantResponseDto::from)
                .toList();
    }

    @Transactional
    public ParticipantResponseDto updateParticipant(Long id, ParticipantUpdateDto dto) {
        Participant participant = findParticipantOrThrow(id);

        if (!participant.getStudentNumber().equals(dto.getStudentNumber())
                && participantRepository.existsByStudentNumberAndEventId(dto.getStudentNumber(), participant.getEventId())) {
            throw new BadRequestException("이미 신청한 행사입니다.");
        }

        participant.setName(dto.getName());
        participant.setStudentNumber(dto.getStudentNumber());

        return ParticipantResponseDto.from(participantRepository.save(participant));
    }

    @Transactional
    public void deleteParticipant(Long id) {
        Participant participant = findParticipantOrThrow(id);
        Long eventId = participant.getEventId();

        participantRepository.delete(participant);
        eventService.decreaseParticipantCount(eventId);
    }

    private Participant findParticipantOrThrow(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 신청 정보입니다."));
    }

    private void validateDuplicateParticipant(String studentNumber, Long eventId) {
        if (participantRepository.existsByStudentNumberAndEventId(studentNumber, eventId)) {
            throw new BadRequestException("이미 신청한 행사입니다.");
        }
    }
}
