package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.dto.VersementGetDto;
import ma.octo.assignement.mapper.VersementMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VersementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class VersementServiceTest {
    @Autowired
    private VersementService versementService;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private VersementRepository versementRepository;
    @Autowired
    private VersementMapper versementMapper;
    Utilisateur utilisateur1;
    Utilisateur utilisateur2;
    Compte compte1;
    Compte compte2;

    private void init1() {
        utilisateur1 = new Utilisateur();
        utilisateur1.setUsername("Username");
        utilisateur1.setLastname("Lastname");
        utilisateur1.setFirstname("Firstname");
        utilisateur1.setGender("Gender");
        utilisateur1.setBirthdate(new Date());

        utilisateur1 = utilisateurRepository.save(utilisateur1);

        compte1 = new Compte();
        compte1.setNrCompte("xxxx");
        compte1.setRib("RIB4");
        compte1.setSolde(new BigDecimal(500L));
        compte1.setUtilisateur(utilisateur1);

        compte1 = compteRepository.save(compte1);
    }

    private void init2() {
        utilisateur2 = new Utilisateur();
        utilisateur2.setUsername("Username 2");
        utilisateur2.setLastname("Lastname 2");
        utilisateur2.setFirstname("Firstname 2");
        utilisateur2.setGender("Gender");

        utilisateur2 = utilisateurRepository.save(utilisateur2);

        compte2 = new Compte();
        compte2.setNrCompte("yyyy");
        compte2.setRib("RIB5");
        compte2.setSolde(new BigDecimal(500L));
        compte2.setUtilisateur(utilisateur2);

        compte2 = compteRepository.save(compte2);
    }

    private void assertVersementEqualByVersement(VersementGetDto versementGetDto1, VersementGetDto versementGetDto2) {
        assertThat(versementGetDto1.getMotif()).isEqualTo(versementGetDto2.getMotif());
        assertThat(versementGetDto1.getMontantVersement()).isEqualTo(versementGetDto2.getMontantVersement());
        assertThat(versementGetDto1.getDate()).isEqualTo(versementGetDto2.getDate());
        assertThat(versementGetDto1.getNom_prenom_emetteur()).isEqualTo(versementGetDto2.getNom_prenom_emetteur());
        assertThat(versementGetDto1.getRibCompteBeneficiaire()).isEqualTo(versementGetDto2.getRibCompteBeneficiaire());
    }


    @Test
    void addVirement(){
        init1();
        init2();

        Versement versement = new Versement();
        versement.setNom_prenom_emetteur("ZAIRI Jalal");
        versement.setCompteBeneficiaire(compte2);
        versement.setDateExecution(new Date());
        versement.setMontant(new BigDecimal(50));
        versement.setMotif("Motif");

        VersementGetDto virementGetDto1 = versementMapper.versementToVersementGetDto(versementRepository.save(versement));
        List<VersementGetDto> virementDtos = versementService.getVersements();
        VersementGetDto virementGetDto2 = virementDtos.get(virementDtos.size() - 1);
        assertVersementEqualByVersement(virementGetDto1,virementGetDto2);
    }
}
