package dev.benswift.consosvehicules.dao;

import dev.benswift.consosvehicules.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Integer> {
	public Categorie findByLibelle(String libelle);
}
