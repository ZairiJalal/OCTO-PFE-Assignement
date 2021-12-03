package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementGetDto;
import ma.octo.assignement.mapper.VirementMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VirementRepository;
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
public class VirementServiceTest {
    @Autowired
    private VirementService virementService;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private VirementRepository virementRepository;
    @Autowired
    private VirementMapper virementMapper;
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

    private void assertVirementEqualByVirement(VirementGetDto virementGetDto1, VirementGetDto virementGetDto2) {
        assertThat(virementGetDto1.getMotif()).isEqualTo(virementGetDto2.getMotif());
        assertThat(virementGetDto1.getMontantVirement()).isEqualTo(virementGetDto2.getMontantVirement());
        assertThat(virementGetDto1.getDate()).isEqualTo(virementGetDto2.getDate());
        assertThat(virementGetDto1.getNrCompteEmetteur()).isEqualTo(virementGetDto2.getNrCompteEmetteur());
        assertThat(virementGetDto1.getNrCompteBeneficiaire()).isEqualTo(virementGetDto2.getNrCompteBeneficiaire());
    }


    @Test
    void addVirement(){
        init1();
        init2();

        Virement virement = new Virement();
        virement.setCompteEmetteur(compte1);
        virement.setCompteBeneficiaire(compte2);
        virement.setDateExecution(new Date());
        virement.setMontant(new BigDecimal(50));
        virement.setMotif("Motif");

        VirementGetDto virementGetDto1 = virementMapper.virementToVirementGetDto(virementRepository.save(virement));
        List<VirementGetDto> virementDtos = virementService.getVirements();
        VirementGetDto virementGetDto2 = virementDtos.get(virementDtos.size() - 1);
        assertVirementEqualByVirement(virementGetDto1,virementGetDto2);
    }
}
