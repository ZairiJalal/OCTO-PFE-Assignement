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
    private VirementMapper virementMapper;

    private VirementRepository virementRepository;

    @Autowired
    public VirementServiceImpl(
            VirementMapper virementMapper,
            VirementRepository virementRepository
    ) {
        this.virementMapper = virementMapper;
        this.virementRepository = virementRepository;
    }


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
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (cmpteBeneficiaire == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (virementPostDto.getMontantVirement().equals(null) || virementPostDto.getMontantVirement().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (virementPostDto.getMontantVirement().intValue() < 10) {
            System.out.println("Montant minimal de virement non atteint");
            throw new MontantMinNonAtteintExeption("Montant minimal de virement non atteint");
        } else if (virementPostDto.getMontantVirement().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de virement dépassé");
            throw new MontantMaxDepasseException("Montant maximal de virement dépassé");
        }

        if (virementPostDto.getMotif().length() < 0) {
            System.out.println("Motif vide");
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
        virementRepository.save(virement);

        auditService.auditVirement("Virement depuis " + virementPostDto.getNrCompteEmetteur() + " vers "
                + virementPostDto.getNrCompteBeneficiaire() + " d'un montant de "
                + virementPostDto.getMontantVirement().toString());

        return virementMapper.virementToVirementGetDto(virement);
    }

}
