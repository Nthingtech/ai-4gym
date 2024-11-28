package org.nthing.prescriptions;

import jakarta.inject.Singleton;
import org.nthing.prescriptions.dtos.PrescriptionDTO;
import org.nthing.prescriptions.mapper.PrescriptionMapper;

@Singleton
public class PrescriptionService {

    private final PrescriptionMapper prescriptionMapper;
    public PrescriptionService(PrescriptionMapper prescriptionMapper) {
        this.prescriptionMapper = prescriptionMapper;
    }

    public PrescriptionDTO createPrescription(PrescriptionDTO newPrescriptionDTO) {
        Prescription newPrescription = prescriptionMapper.toEntityPrescription(newPrescriptionDTO);
        newPrescription.persist();
        return prescriptionMapper.toDTOPrescription(newPrescription);
    }
}
