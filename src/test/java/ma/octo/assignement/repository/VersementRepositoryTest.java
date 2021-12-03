package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Versement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class VersementRepositoryTest {

    @Autowired
    private VersementRepository versementRepository;

    @Autowired
    private CompteRepository compteRepository;

    private Versement versement ;

    private void init() {
        versement = new Versement();
        versement.setNom_prenom_emetteur("ZAIRI Jalal");
        versement.setCompteBeneficiaire(compteRepository.findByRib("RIB1"));
        versement.setMontant(BigDecimal.valueOf(1400L));
        versement.setMotif("Motif");
    }
    private void init2() {
        versement = new Versement();
        versement.setNom_prenom_emetteur("ZAIRI Jalal");
        versement.setCompteBeneficiaire(compteRepository.findByRib("RIB1"));
        versement.setMontant(BigDecimal.valueOf(1400L));
        versement.setMotif("Motif");
        versementRepository.save(versement);

        versement = new Versement();
        versement.setNom_prenom_emetteur("ZAIRI Jalal");
        versement.setCompteBeneficiaire(compteRepository.findByRib("RIB1"));
        versement.setMontant(BigDecimal.valueOf(1400L));
        versement.setMotif("Motif");
        versementRepository.save(versement);
    }


      private void assertVersementEqualByVersement(Versement  versement1, Versement versement2) {
        assertThat(versement1.getId()).isEqualTo(versement2.getId());
        assertThat(versement1.getMotif()).isEqualTo(versement2.getMotif());
        assertThat(versement1.getMontant()).isEqualTo(versement2.getMontant());
        assertThat(versement1.getDateExecution()).isEqualTo(versement2.getDateExecution());
        assertThat(versement1.getNom_prenom_emetteur()).isEqualTo(versement2.getNom_prenom_emetteur());
        assertThat(versement1.getCompteBeneficiaire()).isEqualTo(versement2.getCompteBeneficiaire());
      }


    @Test
    void findOne() {
        init();
        Versement addVersement = versementRepository.save(versement);
        Optional<Versement> foundVersement =versementRepository.findById(addVersement.getId());

        assertThat(foundVersement).isPresent();
        assertVersementEqualByVersement(foundVersement.get(),versement);
    }

    @Test
    void findAll() {
          init2();
        List<Versement> versementList = versementRepository.findAll();

        assertThat(versementList).isNotNull();
        assertThat(versementList.size()).isEqualTo(2);
        assertThat(versementList).hasSameClassAs(new ArrayList<Versement>());
    }

    @Test
    void save() {
        init();
        Versement foundVersement = versementRepository.save(versement);

        assertNotNull(foundVersement);
        assertVersementEqualByVersement(foundVersement,versement);
    }

    @Test
    void delete() {
        init();
        Versement addVersement = versementRepository.save(versement);
        versementRepository.delete(addVersement);
        Optional<Versement> foundVersement = versementRepository.findById(addVersement.getId());

        assertThat(foundVersement).isNotPresent();
    }

}
