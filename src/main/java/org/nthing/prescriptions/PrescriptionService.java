package org.nthing.prescriptions;

import org.nthing.prescriptions.mapper.PrescriptionMapper;

public class PrescriptionService {

    private final PrescriptionMapper prescriptionMapper;
    public PrescriptionService(PrescriptionMapper prescriptionMapper) {
        this.prescriptionMapper = prescriptionMapper;
    }


}
