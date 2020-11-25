package dev.benswift.consosvehicules.dao;

import dev.benswift.consosvehicules.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule,Integer> {
}
