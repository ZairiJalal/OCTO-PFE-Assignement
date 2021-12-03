package ma.octo.assignement.service.Impl;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementGetDto;
import ma.octo.assignement.dto.VirementPostDto;
import ma.octo.assignement.exceptions.*;
import ma.octo.assignement.mapper.VirementMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VirementRepository;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class VirementServiceImpl implements VirementService {

    public static final int MONTANT_MAXIMAL = 10000;
    public static final int MONTANT_MINIMAL = 10;

    @Autowired
    private VirementMapper virementMapper;
    @Autowired
    private VirementRepository virementRepository;
   /* private VirementMapper virementMapper;

    private VirementRepository virementRepository;

    @Autowired
    public VirementServiceImpl(
            VirementMapper virementMapper,
            VirementRepository virementRepository
    ) {
        this.virementMapper = virementMapper;
        this.virementRepository = virementRepository;
    }*/


    @Autowired
    CompteRepository compteRepository;
    @Autowired
    AuditService auditService;

    @Override
    public List<VirementGetDto> getVirements() {
        List<Virement> virements = virementRepository.findAll();
        List<VirementGetDto> virementsDto = new ArrayList<>();
        for(Virement virement : virements){
            virementsDto.add(virementMapper.virementToVirementGetDto(virement));
        }
        return virementsDto;
    }

    @Override
    public VirementGetDto addVirement(VirementPostDto virementPostDto) throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException, MotifVideException, MontantMinNonAtteintExeption, MontantMaxDepasseException {

        Compte compteEmetteur = compteRepository.findByNrCompte(virementPostDto.getNrCompteEmetteur());
        Compte cmpteBeneficiaire = compteRepository.findByNrCompte(virementPostDto.getNrCompteBeneficiaire());

        if (compteEmetteur == null) {
            throw new CompteNonExistantException("Compte Emetteur Non existant");
        }

        if (cmpteBeneficiaire == null) {
            throw new CompteNonExistantException("Compte Beneficiaire Non existant");
        }

        if (virementPostDto.getMontantVirement().equals(null) || virementPostDto.getMontantVirement().intValue() == 0) {
            throw new TransactionException("Montant vide");
        } else if (virementPostDto.getMontantVirement().intValue() < MONTANT_MINIMAL) {
            throw new MontantMinNonAtteintExeption("Montant minimal de virement non atteint"+MONTANT_MINIMAL);
        } else if (virementPostDto.getMontantVirement().intValue() > MONTANT_MAXIMAL) {
            throw new MontantMaxDepasseException("Montant maximal de virement dépassé"+MONTANT_MAXIMAL);
        }

        if (virementPostDto.getMotif().length() == 0 || virementPostDto.getMotif().equals(null)) {
            throw new MotifVideException("Motif vide");
        }

        if (compteEmetteur.getSolde().intValue() - virementPostDto.getMontantVirement().intValue() < 0) {
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant pour l'utilisateur");
        }


        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(virementPostDto.getMontantVirement()));
        compteRepository.save(compteEmetteur);

        cmpteBeneficiaire.setSolde(cmpteBeneficiaire.getSolde().add(virementPostDto.getMontantVirement()));
        compteRepository.save(cmpteBeneficiaire);
        Virement virement = virementMapper.virementPostDtoToVirement(virementPostDto);
        virement = virementRepository.save(virement);

        auditService.auditVirement("Virement depuis " + virement.getCompteEmetteur().getNrCompte() + " vers "
                + virement.getCompteBeneficiaire().getNrCompte() + " d'un montant de "
                + virement.getMontant().toString());

        return virementMapper.virementToVirementGetDto(virement);
    }

}
