package org.nthing.prescriptions.dtos;

import java.time.LocalDateTime;

public record PrescriptionDTO(String namePrescription,
                              LocalDateTime startPrescription,
                              LocalDateTime endPrescription,
                              Integer totalPrescription,
                              Integer completedWorkout,
                              Long IdClientDTO) {
}
