package org.nthing.prescriptions.mapper;

import org.nthing.persons.clients.mapper.ClientMapper;
import org.nthing.prescriptions.Prescription;
import org.nthing.prescriptions.dtos.PrescriptionDTO;

public class PrescriptionMapper {

    private final ClientMapper clientMapper;
    public PrescriptionMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    public PrescriptionDTO toDTOPrescription(Prescription prescription) {
        return new PrescriptionDTO(
                prescription.namePrescription,
                prescription.startPrescription,
                prescription.endPrescription,
                prescription.totalPrescription,
                prescription.completedWorkout,
                clientMapper.toDTOBuilder(prescription.client)

        );
    }

    public Prescription toEntityPrescription(PrescriptionDTO prescriptionDTO) {
        var prescription = new Prescription();

        prescription.namePrescription = prescriptionDTO.namePrescription();
        prescription.startPrescription = prescriptionDTO.startPrescription();
        prescription.endPrescription = prescriptionDTO.endPrescription();
        prescription.completedWorkout = prescriptionDTO.completedWorkout();
        clientMapper.toEntityBuilder(prescriptionDTO.clientDTO());
        return prescription;
    }
}
