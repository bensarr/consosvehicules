package dev.benswift.consosvehicules.dao;

import dev.benswift.consosvehicules.model.SousCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SousCategorieRepository extends JpaRepository<SousCategorie,Integer> {
    public List<SousCategorie> findAll ();
}
