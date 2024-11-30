package org.nthing.prescriptions;

import jakarta.inject.Singleton;
import org.nthing.persons.clients.Client;
import org.nthing.prescriptions.dtos.PrescriptionDTO;
import org.nthing.prescriptions.mapper.PrescriptionMapper;

@Singleton
public class PrescriptionService {

    private final PrescriptionMapper prescriptionMapper;
    private final Client client;
    public PrescriptionService(PrescriptionMapper prescriptionMapper, Client client) {
        this.prescriptionMapper = prescriptionMapper;
        this.client = client;
    }

    public PrescriptionDTO createPrescription(PrescriptionDTO newPrescriptionDTO) {
        Client clientEntity = Client.findById(newPrescriptionDTO.IdClientDTO());
        Prescription newPrescription = prescriptionMapper.toEntityPrescription(newPrescriptionDTO);
        newPrescription.client = clientEntity;
        newPrescription.persist();
        return prescriptionMapper.toDTOPrescription(newPrescription);
    }
}
