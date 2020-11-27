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

import java.util.ArrayList;
import java.util.List;

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
        this.rolePrerequis("ROLE_ADMIN");
        this.rolePrerequis("ROLE_RESPONSABLE");
        this.utilisateurPrerequis("admin");
        System.out.println("*************  ok  **"+utilisateurRepository.findAll().size()+"**********************");

    }
    private void rolePrerequis(String libelle)
    {
        if(!roleRepository.existsByLibelle(libelle))
        {
            Role r=new Role();
            r.setLibelle(libelle);
            roleRepository.save(r);
        }
    }
    private void utilisateurPrerequis(String username)
    {
        if(!utilisateurRepository.existsByUsername(username))
        {
            List<Role> roles=new ArrayList<>();
            roles.add(roleRepository.findByLibelle("ROLE_ADMIN"));
            Utilisateur u=new Utilisateur();
            u.setUsername(username);
            u.setNom("ADMIN");
            u.setPrenom("Admin");
            u.setPassword(encoder.encode("brandao37"));
            Utilisateur user=utilisateurRepository.save(u);
            user.setRoles(roles);
            utilisateurRepository.save(user);
        }
    }
}
