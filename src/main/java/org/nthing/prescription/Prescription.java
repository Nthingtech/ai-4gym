package org.nthing.prescription;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.nthing.persons.client.Client;

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

    public static List<Prescription> findPrescriptionClient(Long clientId) {
        return find("client.id", clientId).list();
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Client client;

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
