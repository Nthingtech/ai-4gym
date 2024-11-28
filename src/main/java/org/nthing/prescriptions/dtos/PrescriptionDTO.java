package org.nthing.prescriptions.dtos;

import org.nthing.persons.clients.dtos.ClientDTO;

import java.time.LocalDateTime;

public record PrescriptionDTO(String namePrescription,
                              LocalDateTime startPrescription,
                              LocalDateTime endPrescription,
                              Integer totalPrescription,
                              Integer completedWorkout,
                              ClientDTO clientDTO) {
}
