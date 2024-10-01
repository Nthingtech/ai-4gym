package org.nthing.persons.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.Period;

@ApplicationScoped
public class ClientService {


    public Client createClient(@Valid Client newClient) {
        LocalDate today = LocalDate.now();
        Period calcAge = Period.between(newClient.birthDate, today);
        newClient.age = calcAge.getYears();
        newClient.persist();
        return newClient;
    }

    public Client findBYId(Long id) {
        return Client.findById(id);
    }
}
