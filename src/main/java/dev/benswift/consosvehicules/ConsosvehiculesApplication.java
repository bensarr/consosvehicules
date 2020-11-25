package dev.benswift.consosvehicules;

import dev.benswift.consosvehicules.dao.RoleRepository;
import dev.benswift.consosvehicules.dao.UtilisateurRepository;
import dev.benswift.consosvehicules.model.Role;
import dev.benswift.consosvehicules.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ConsosvehiculesApplication implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(ConsosvehiculesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*Role r1=new Role();
        Role r2=new Role();
        r1.setLibelle("ROLE_ADMIN");
        r2.setLibelle("ROLE_RESPONSABLE");
        roleRepository.save(r1);
        roleRepository.save(r2);
        Utilisateur u=new Utilisateur();
        u.setUsername("admin");
        u.setNom("ADMIN");
        u.setPrenom("Admin");
        //u.setRoles(roles);
        u.setPassword(encoder.encode("brandao37"));
        utilisateurRepository.save(u);*/
        System.out.println("***************"+utilisateurRepository.findAll().size()+"**********************");

    }
}
