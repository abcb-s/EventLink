package org.example.eventlink.event.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EventRequestDto {

    private String title;

    private String description;

    private String location;

    private LocalDate eventDate;

    private Integer maxParticipants;
}
