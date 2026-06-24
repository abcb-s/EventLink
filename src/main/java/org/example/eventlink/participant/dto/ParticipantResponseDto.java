package org.example.eventlink.participant.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.eventlink.participant.entity.Participant;

import java.time.LocalDateTime;

@Getter
@Builder
public class ParticipantResponseDto {

    private Long id;

    private String name;

    private String studentNumber;

    private Long eventId;

    private LocalDateTime appliedAt;

    public static ParticipantResponseDto from(Participant participant) {
        return ParticipantResponseDto.builder()
                .id(participant.getId())
                .name(participant.getName())
                .studentNumber(participant.getStudentNumber())
                .eventId(participant.getEventId())
                .appliedAt(participant.getAppliedAt())
                .build();
    }
}
