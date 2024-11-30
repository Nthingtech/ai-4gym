package org.nthing.prescriptions;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.nthing.prescriptions.dtos.PrescriptionDTO;

import java.util.List;

@Path("prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    public PrescriptionController( PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public PrescriptionDTO create(PrescriptionDTO prescriptionDTO) {
        return prescriptionService.createPrescription(prescriptionDTO);
    }

    @GET
    @Path("find-by-client/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> prescriptionsByClient(Long clientId) {
        return Prescription.findPrescriptionClient(clientId);
    }

    @GET
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> prescriptionList() {
        return Prescription.listAll();
    }
}
