package dev.benswift.consosvehicules.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelle;
    @OneToMany(mappedBy = "categorie")
    private List<SousCategorie> sousCategorie;


    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<SousCategorie> getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(List<SousCategorie> sousCategorie) {
        this.sousCategorie = sousCategorie;
    }
}
