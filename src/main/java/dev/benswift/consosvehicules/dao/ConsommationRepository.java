package dev.benswift.consosvehicules.dao;

import dev.benswift.consosvehicules.model.Consommation;
import dev.benswift.consosvehicules.model.Vehicule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsommationRepository extends JpaRepository<Consommation,Integer> {
    public List<Consommation> findAll();
    public List<Consommation> findAllByVehicule(Vehicule vehicule);
}
