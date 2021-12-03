package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Versement;

import ma.octo.assignement.domain.Virement;
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
class VirementRepositoryTest {

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private CompteRepository compteRepository;

    private Virement virement ;

    private void init() {
        virement = new Virement();
        virement.setCompteEmetteur(compteRepository.findByRib("RIB2"));
        virement.setCompteBeneficiaire(compteRepository.findByRib("RIB1"));
        virement.setMontant(BigDecimal.valueOf(450L));
        virement.setMotif("Motif");
    }
    private void init2() {
        virement = new Virement();
        virement.setCompteEmetteur(compteRepository.findByRib("RIB2"));
        virement.setCompteBeneficiaire(compteRepository.findByRib("RIB1"));
        virement.setMontant(BigDecimal.valueOf(450L));
        virement.setMotif("Motif");
        virementRepository.save(virement);

        virement = new Virement();
        virement.setCompteEmetteur(compteRepository.findByRib("RIB2"));
        virement.setCompteBeneficiaire(compteRepository.findByRib("RIB1"));
        virement.setMontant(BigDecimal.valueOf(17L));
        virement.setMotif("Motif");
        virementRepository.save(virement);
    }

    private void assertVirementEqualByVirement(Virement  virement1, Virement virement2) {
        assertThat(virement1.getId()).isEqualTo(virement2.getId());
        assertThat(virement1.getMotif()).isEqualTo(virement2.getMotif());
        assertThat(virement1.getMontant()).isEqualTo(virement2.getMontant());
        assertThat(virement1.getDateExecution()).isEqualTo(virement2.getDateExecution());
        assertThat(virement1.getCompteEmetteur()).isEqualTo(virement2.getCompteEmetteur());
        assertThat(virement1.getCompteBeneficiaire()).isEqualTo(virement2.getCompteBeneficiaire());
    }


    @Test
    void findOne() {
        init();
        Virement addVirsement = virementRepository.save(virement);
        Optional<Virement> foundVersement =virementRepository.findById(addVirsement.getId());

        assertThat(foundVersement).isPresent();
        assertVirementEqualByVirement(foundVersement.get(),virement);
    }

    @Test
    void findAll() {
        init2();
        List<Virement> virementList = virementRepository.findAll();

        assertThat(virementList).isNotNull();
        assertThat(virementList.size()).isEqualTo(3);
        assertThat(virementList).hasSameClassAs(new ArrayList<Virement>());
    }

    @Test
    void save() {
        init();
        Virement foundVersement = virementRepository.save(virement);

        assertNotNull(foundVersement);
        assertVirementEqualByVirement(foundVersement,virement);
    }

    @Test
    void delete() {
        init();
        Virement addVirement = virementRepository.save(virement);
        virementRepository.delete(addVirement);
        Optional<Virement> foundVersement = virementRepository.findById(addVirement.getId());

        assertThat(foundVersement).isNotPresent();
    }

}
