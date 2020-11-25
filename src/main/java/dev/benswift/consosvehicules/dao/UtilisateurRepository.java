package dev.benswift.consosvehicules.dao;

import dev.benswift.consosvehicules.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    public Utilisateur findByUsername(String username);
    public boolean existsByUsername(String username);
}
