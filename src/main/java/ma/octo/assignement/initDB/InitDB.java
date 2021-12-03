package ma.octo.assignement.initDB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import ma.octo.assignement.domain.*;
import ma.octo.assignement.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VirementRepository;


@Component
public class InitDB implements ApplicationRunner{

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private VirementRepository virementRepository;
    /*@Autowired
    AppUserService appUserService;*/

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setUsername("user1");
        utilisateur1.setLastname("last1");
        utilisateur1.setFirstname("first1");
        utilisateur1.setGender("Male");

        utilisateurRepository.save(utilisateur1);


        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setUsername("user2");
        utilisateur2.setLastname("last2");
        utilisateur2.setFirstname("first2");
        utilisateur2.setGender("Female");

        utilisateurRepository.save(utilisateur2);

        Compte compte1 = new Compte();
        compte1.setNrCompte("010000A000001000");
        compte1.setRib("RIB1");
        compte1.setSolde(BigDecimal.valueOf(200000L));
        compte1.setUtilisateur(utilisateur1);

        compteRepository.save(compte1);

        Compte compte2 = new Compte();
        compte2.setNrCompte("010000B025001000");
        compte2.setRib("RIB2");
        compte2.setSolde(BigDecimal.valueOf(140000L));
        compte2.setUtilisateur(utilisateur2);

        compteRepository.save(compte2);

        Virement v = new Virement();
        v.setMontant(BigDecimal.TEN);
        v.setCompteBeneficiaire(compte2);
        v.setCompteEmetteur(compte1);
        v.setDateExecution(new Date());
        v.setMotif("Assignment 2021");

        virementRepository.save(v);

        /*appUserService.addNewRole(new AppRole(null,"USER"));
        appUserService.addNewRole(new AppRole(null,"ADMIN"));
        appUserService.addNewUser(new AppUser(null,"user1","1234",new ArrayList<>()));
        appUserService.addNewUser(new AppUser(null,"admin","1234",new ArrayList<>()));
        appUserService.addNewUser(new AppUser(null,"user2","1234",new ArrayList<>()));
        appUserService.addNewUser(new AppUser(null,"user3","1234",new ArrayList<>()));
        appUserService.addNewUser(new AppUser(null,"user4","1234",new ArrayList<>()));
        appUserService.addRoleToUser("user1","USER");
        appUserService.addRoleToUser("admin","USER");
        appUserService.addRoleToUser("user2","USER");
        appUserService.addRoleToUser("user3","USER");
        appUserService.addRoleToUser("user4","USER");
        appUserService.addRoleToUser("admin","ADMIN");*/

    }
}
