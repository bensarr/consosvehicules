package dev.benswift.consosvehicules.model;

public final class ViewModelConsommation {
	private String mois;
	private int carburant;
	private int entretien;
	private int reparation;
	private int divers;
	private int total;
	public ViewModelConsommation() {
	}
	public String getMois() {
		return mois;
	}
	public void setMois(String mois) {
		this.mois = mois;
	}
	public int getCarburant() {
		return carburant;
	}
	public void setCarburant(int carburant) {
		this.carburant = carburant;
	}
	public int getEntretien() {
		return entretien;
	}
	public void setEntretien(int entretien) {
		this.entretien = entretien;
	}
	public int getReparation() {
		return reparation;
	}
	public void setReparation(int reparation) {
		this.reparation = reparation;
	}
	public int getDivers() {
		return divers;
	}
	public void setDivers(int divers) {
		this.divers = divers;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal() {
		this.total = this.carburant+this.entretien+this.reparation+this.divers;
	}
	
	
	

}
