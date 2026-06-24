package org.example.eventlink.participant.repository;

import org.example.eventlink.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    boolean existsByStudentNumberAndEventId(String studentNumber, Long eventId);

    long countByEventId(Long eventId);
}
