package org.nthing.prescription;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("prescriptions")
public class PrescriptionController {

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void create(Prescription prescription) {
        prescription.persist();
    }

    @GET
    @Path("find-by-client/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> prescriptionsByClient(Long clientId) {
        return Prescription.findPrescriptionClient(clientId);
    }
}
