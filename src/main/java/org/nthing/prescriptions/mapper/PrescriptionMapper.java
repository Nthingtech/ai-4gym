package org.nthing.prescriptions.mapper;

import org.mapstruct.Mapper;
import org.nthing.prescriptions.Prescription;
import org.nthing.prescriptions.dtos.PrescriptionDTO;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface PrescriptionMapper {

    Prescription toEntityPrescription(PrescriptionDTO prescriptionDTO);

    PrescriptionDTO toDTOPrescription(Prescription prescription);

    List<Prescription> toEntityListPrescription(List<PrescriptionDTO> prescriptionDTOList);

    List<PrescriptionDTO> toDtoListPrescription(List<Prescription> prescription);
}
