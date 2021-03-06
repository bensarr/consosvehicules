package dev.benswift.consosvehicules.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.benswift.consosvehicules.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Role findByLibelle(String libelle);
    public boolean existsByLibelle(String libelle);
}
