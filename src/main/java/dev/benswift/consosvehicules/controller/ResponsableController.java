package dev.benswift.consosvehicules.controller;

import dev.benswift.consosvehicules.dao.AgentRepository;
import dev.benswift.consosvehicules.dao.CategorieRepository;
import dev.benswift.consosvehicules.dao.ConsommationRepository;
import dev.benswift.consosvehicules.dao.SousCategorieRepository;
import dev.benswift.consosvehicules.dao.VehiculeRepository;
import dev.benswift.consosvehicules.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@PreAuthorize("hasAuthority('ROLE_RESPONSABLE')")
@Controller
@RequestMapping("/responsable")
public class ResponsableController {

    @Autowired
    AgentRepository agentRepository;
    @Autowired
    VehiculeRepository vehiculeRepository;
    @Autowired
    ConsommationRepository consommationRepository;
    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    SousCategorieRepository souscategorieRepository;
    private 
    String [] tabMois={"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
    private
    List<String> tabAnnee=allYears(2020);

    @GetMapping("/index")
    public String Index(Model model,
            @RequestParam(name="search_vehicule",defaultValue = "")String search_vehicule,
            @RequestParam(name="search_annee",defaultValue = "")String search_annee)
    {
    	model.addAttribute("vehicules",vehiculeRepository.findAll());
        model.addAttribute("tabAnnee", this.tabAnnee);
        List<Consommation> consommations;
        if(search_vehicule.equals(""))
            consommations=consommationRepository
                    .findAll();
        else
            consommations=consommationRepository
                .findAllByVehicule(
                        vehiculeRepository.getOne(Integer.parseInt(search_vehicule))
                );
        if(!search_annee.equals(""))
        	consommations=this.resultAnnee(consommations, search_annee);
        
        List<ViewModelConsommation> viewModels=this.getViewModel(consommations);
        model.addAttribute("viewModel",viewModels);
        model.addAttribute("model",this.total(viewModels));
        return "responsable/index";
    }
    @GetMapping("/vehicules")
    public String Vehicules(Model model)
    {

        Vehicule vehicule=new Vehicule();
        Agent agent=new Agent();

        List<Vehicule> vehicules=vehiculeRepository.findAll();
        List<Agent> agents=agentRepository.findAll();

        model.addAttribute("agent",agent);
        model.addAttribute("agents",agents);
        model.addAttribute("vehicule",vehicule);
        model.addAttribute("vehicules",vehicules);
        return "responsable/vehicule";
    }
    @GetMapping("/consommations")
    public String Consommations(Model model,
                                @RequestParam(name="search_categorie",defaultValue = "")String search_categorie,
                                @RequestParam(name="search_vehicule",defaultValue = "")String search_vehicule,
                                @RequestParam(name="search_mois",defaultValue = "")String search_mois,
                                @RequestParam(name="search_annee",defaultValue = "")String search_annee)
    {
        Consommation consommation=new Consommation();

        List<Vehicule> vehicules=vehiculeRepository.findAll();
        List<SousCategorie> souscategories=souscategorieRepository.findAll();
        model.addAttribute("vehicules",vehicules);
        model.addAttribute("souscategories",souscategories);
        model.addAttribute("categories",categorieRepository.findAll());
        model.addAttribute("tabMois", Arrays.asList(this.tabMois));
        model.addAttribute("tabAnnee", tabAnnee);

        List<Consommation> consommations;
        if(search_vehicule.equals(""))
            consommations=consommationRepository
                    .findAll();
        else
            consommations=consommationRepository
                .findAllByVehicule(
                        vehiculeRepository.getOne(Integer.parseInt(search_vehicule))
                );
        if(!search_categorie.equals(""))
        	consommations=this.resultCategorie(consommations, search_categorie);
        if(!search_mois.equals(""))
        	consommations=this.resultMois(consommations, search_mois);
        if(!search_annee.equals(""))
        	consommations=this.resultAnnee(consommations, search_annee);
        model.addAttribute("consommation",consommation);
        
        Collections.reverse(consommations);
        model.addAttribute("consommations",consommations);

        model.addAttribute("search_categorie",search_categorie);
        model.addAttribute("search_vehicule",search_vehicule);
        model.addAttribute("search_mois",search_mois);
        model.addAttribute("search_annee",search_annee);
        return "responsable/consommations";//?search_vehicule="+search_vehicule+"&search_categorie="+search_categorie+"&search_mois="+search_mois+"&search_annee="+search_annee;
    }

    @PostMapping("/consommation/add")
    public String consommationAdd(@ModelAttribute("consommation") Consommation consommation) {
        try {
            consommationRepository.save(consommation);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/responsable/consommations";
    }
    @PostMapping("/vehicule/add")
    public String vehiculeAdd(@ModelAttribute("vehicule") Vehicule vehicule) {
        try {
            vehicule.setDateInscription(LocalDateTime.now());
            vehiculeRepository.save(vehicule);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/responsable/vehicules";
    }
    @PostMapping("/vehicule/remove")
    public String vehiculeRemove(int vehiculeId) {
        Vehicule v = vehiculeRepository.getOne(vehiculeId);
        vehiculeRepository.delete(v);
        return "redirect:/responsable/vehicules";
    }
    @GetMapping("/vehicule/{id}")
    public @ResponseBody
    Vehicule vehiculeGet(@PathVariable(name = "id") int vehiculeId){
        return vehiculeRepository.findById(vehiculeId).get();
    }
    @PostMapping("/agent/add")
    public String agentAdd(@ModelAttribute("agent") Agent agent) {
        try {
            agent.setDateInscription(LocalDateTime.now());
            agentRepository.save(agent);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/responsable/vehicules";
    }
    @PostMapping("/agent/remove")
    public String agentRemove(int agentId) {
        Agent a = agentRepository.getOne(agentId);
        agentRepository.delete(a);
        return "redirect:/responsable/vehicules";
    }
    private List<String> allYears(int firstYear)
    {
        List<String> list=new ArrayList<>();
        list.add(String.valueOf(firstYear));
        int lastYear= LocalDate.now().getYear();
        while (lastYear>firstYear)
        {
            firstYear+=1;
            list.add(String.valueOf(firstYear));
        }
        return list;
    }
    private List<Consommation> resultCategorie(List<Consommation> consommations, String categorie)
    {
    	List<Consommation> result= new ArrayList<>();
    	
    	consommations.forEach(c->
    	{
    		if(c.getSousCategorie().getCategorie().getId()==categorieRepository.getOne(Integer.parseInt(categorie)).getId())
    			result.add(c);
    	}
    			);
    	return result;
    }

    private List<Consommation> resultMois(List<Consommation> consommations, String mois)
    {
    	List<Consommation> result= new ArrayList<>();
    	
    	consommations.forEach(c->
    	{
    		if(c.getDate().getMonthValue()==Integer.parseInt(mois))
    			result.add(c);
    	}
    			);
    	return result;
    }

    private List<Consommation> resultAnnee(List<Consommation> consommations, String annee)
    {
    	List<Consommation> result= new ArrayList<>();
    	
    	consommations.forEach(c->
    	{
    		if(c.getDate().getYear()==Integer.parseInt(annee))
    			result.add(c);
    	}
    			);
    	return result;
    }
    private List<ViewModelConsommation> getViewModel(List<Consommation> consommations)
    {
    	List<ViewModelConsommation> viewModel=new ArrayList<>();
    	List<String> list=Arrays.asList(this.tabMois);
    	for (String m : list) {

        	ViewModelConsommation model=new ViewModelConsommation();
    		model.setMois(m);
    		model.setCarburant(
    				consommations.stream()
		    		.filter(c->c.getDate().getMonthValue()==(list.indexOf(m)+1))
		    		.filter(c->c.getSousCategorie().getCategorie().getId()==categorieRepository.findByLibelle("Carburant").getId())
		    		.mapToInt(c->c.getMontant()).sum()
		    		);
    		model.setEntretien(
		    		consommations.stream()
		    		.filter(c->c.getDate().getMonthValue()==(list.indexOf(m)+1))
		    		.filter(c->c.getSousCategorie().getCategorie().getId()==categorieRepository.findByLibelle("Entretien").getId())
		    		.mapToInt(c->c.getMontant()).sum()
    				);
    		model.setReparation(
		    		consommations.stream()
		    		.filter(c->c.getDate().getMonthValue()==(list.indexOf(m)+1))
		    		.filter(c->c.getSousCategorie().getCategorie().getId()==categorieRepository.findByLibelle("Reparation").getId())
		    		.mapToInt(c->c.getMontant()).sum()
    				);
    		model.setDivers(
		    		consommations.stream()
		    		.filter(c->c.getDate().getMonthValue()==(list.indexOf(m)+1))
		    		.filter(c->c.getSousCategorie().getCategorie().getId()==categorieRepository.findByLibelle("Divers").getId())
		    		.mapToInt(c->c.getMontant()).sum()
    				);
    		model.setTotal();
    		viewModel.add(model);
		}
    	/*ViewModelConsommation model=new ViewModelConsommation();
    	model.setMois("Total Annuel");
    	model.setCarburant(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getCarburant()).sum()
	    		);
		model.setEntretien(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getEntretien()).sum()
				);
		model.setReparation(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getReparation()).sum()
				);
		model.setDivers(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getDivers()).sum()
				);
		model.setTotal();
		viewModel.add(model);*/
    	
    	return viewModel;
    }
    private ViewModelConsommation total(List<ViewModelConsommation> viewModel) {
    	ViewModelConsommation model=new ViewModelConsommation();
    	model.setMois("Total Annuel");
    	model.setCarburant(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getCarburant()).sum()
	    		);
		model.setEntretien(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getEntretien()).sum()
				);
		model.setReparation(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getReparation()).sum()
				);
		model.setDivers(
				viewModel.stream()
	    		.filter(v->true)
	    		.mapToInt(v->v.getDivers()).sum()
				);
		model.setTotal();
		return model;
	}
}
