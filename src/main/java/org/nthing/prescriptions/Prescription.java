package org.nthing.prescriptions;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.nthing.persons.clients.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Prescription extends PanacheEntity {

    public String namePrescription;

    public LocalDateTime startPrescription;

    public LocalDateTime endPrescription;

    public Integer totalPrescription;

    public Integer completedWorkout;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Client client;

    public static List<Prescription> findPrescriptionClient(Long clientId) {
        return find("SELECT p FROM Prescription p JOIN FETCH p.client c WHERE c.id = ?1", clientId).list(); //TODO converter projection
    }

    public  static List<Prescription> prescriptionsByDate() {
        return find("ORDER BY starPrescription").list();
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
