package dev.benswift.consosvehicules.controller;

import dev.benswift.consosvehicules.dao.CategorieRepository;
import dev.benswift.consosvehicules.dao.RoleRepository;
import dev.benswift.consosvehicules.dao.SousCategorieRepository;
import dev.benswift.consosvehicules.dao.UtilisateurRepository;
import dev.benswift.consosvehicules.model.Categorie;
import dev.benswift.consosvehicules.model.Role;
import dev.benswift.consosvehicules.model.SousCategorie;
import dev.benswift.consosvehicules.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    SousCategorieRepository sousCategorieRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    BCryptPasswordEncoder encoder;
    @GetMapping("/index")
    public String Index(Model model)
    {
        Role role=new Role();
        Categorie categorie=new Categorie();
        SousCategorie sousCategorie=new SousCategorie();
        Utilisateur utilisateur=new Utilisateur();


        List<Role> roles=roleRepository.findAll();
        List<Categorie> categories=categorieRepository.findAll();
        List<SousCategorie> sousCategories=sousCategorieRepository.findAll();
        List<Utilisateur> utilisateurs=utilisateurRepository.findAll();


        model.addAttribute("role",role);
        model.addAttribute("roles",roles);
        model.addAttribute("categorie",categorie);
        model.addAttribute("categories",categories);
        model.addAttribute("sousCategorie",sousCategorie);
        model.addAttribute("sousCategories",sousCategories);
        model.addAttribute("utilisateur",utilisateur);
        model.addAttribute("utilisateurs",utilisateurs);
        return "admin/index";
    }

    @PostMapping("/utilisateur/add")
    public String utilisateurAdd(@ModelAttribute("utilisateur") Utilisateur utilisateur,int role) {
        try {
            List<Role> roles=new ArrayList<>();
            roles.add(roleRepository.getOne(role));
            utilisateur.setRoles(roles);
            utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));
            utilisateurRepository.save(utilisateur);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/index";
    }
    @PostMapping("/utilisateur/remove")
    public String utilisateurRemove(int utilisateurId) {
        Utilisateur u = utilisateurRepository.getOne(utilisateurId);
        utilisateurRepository.delete(u);
        return "redirect:/admin/index";
    }
    @GetMapping("/utilisateur/{id}")
    public @ResponseBody
    Utilisateur utilisateurGet(@PathVariable(name = "id") int utId){
        return utilisateurRepository.findById(utId).get();
    }

    @PostMapping("/role/add")
    public String roleAdd(@ModelAttribute("role") Role role) {
        try {
            roleRepository.save(role);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/index";
    }
    @PostMapping("/role/remove")
    public String roleRemove(int roleId) {
        Role r = roleRepository.getOne(roleId);
        roleRepository.delete(r);
        return "redirect:/admin/index";
    }
    @PostMapping("/categorie/add")
    public String categorieAdd(@ModelAttribute("categorie") Categorie categorie) {
        try {
            categorieRepository.save(categorie);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/index";
    }
    @PostMapping("/categorie/remove")
    public String categorieRemove(int categorieId) {
        Categorie c = categorieRepository.getOne(categorieId);
        sousCategorieRepository.deleteAll(c.getSousCategorie());
        categorieRepository.delete(c);
        return "redirect:/admin/index";
    }
    @PostMapping("/sousCategorie/add")
    public String sousCategorieAdd(@ModelAttribute("sousCategorie") SousCategorie sousCategorie) {
        try {
            sousCategorieRepository.save(sousCategorie);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/index";
    }
    @PostMapping("/sousCategorie/remove")
    public String sousCategorieRemove(int sousCategorieId) {
        SousCategorie sc = sousCategorieRepository.getOne(sousCategorieId);
        sousCategorieRepository.delete(sc);
        return "redirect:/admin/index";
    }
}
