package dev.benswift.consosvehicules.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
public class Vehicule{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String matricule;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateInscription;
    @OneToOne
    @JoinColumn(name="agent_id")
    private Agent agent;
    @OneToMany(mappedBy = "vehicule")
    private List<Consommation> consommation;

    public Vehicule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }


    public List<Consommation> getConsommation() {
        return consommation;
    }

    public void setConsommation(List<Consommation> consommation) {
        this.consommation = consommation;
    }
}
