package ma.octo.assignement.service.Impl;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VersementGetDto;
import ma.octo.assignement.dto.VersementPostDto;
import ma.octo.assignement.exceptions.*;
import ma.octo.assignement.mapper.VersementMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VersementRepository;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class VersementServiceImpl implements VersementService {
    public static final int MONTANT_MAXIMAL = 10000;

    @Autowired
    VersementRepository versementRepository;
    @Autowired
    VersementMapper versementMapper;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    AuditService auditService;

    @Override
    public List<VersementGetDto> getVersements() {
        List<Versement> versements = versementRepository.findAll();
        List<VersementGetDto> versementsDto = new ArrayList<>();
        for(Versement versement : versements){
            versementsDto.add(versementMapper.versementToVersementGetDto(versement));
        }
        return versementsDto;
    }

    @Override
    public VersementGetDto addVersement(VersementPostDto versementPostDto) throws CompteNonExistantException, TransactionException, MontantMinNonAtteintExeption, MontantMaxDepasseException, MotifVideException {
        String nom_prenom_emetteur = versementPostDto.getNom_prenom_emetteur();
        Compte cmpteBeneficiaire = compteRepository.findByRib(versementPostDto.getRibCompteBeneficiaire());

        if (nom_prenom_emetteur == null) {
            System.out.println("nom_prenom_emetteur ??");
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (cmpteBeneficiaire == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (versementPostDto.getMontantVersement().equals(null) || versementPostDto.getMontantVersement().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (versementPostDto.getMontantVersement().intValue() < 10) {
            System.out.println("Montant minimal de virement non atteint");
            throw new MontantMinNonAtteintExeption("Montant minimal de virement non atteint");
        } else if (versementPostDto.getMontantVersement().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de virement dépassé");
            throw new MontantMaxDepasseException("Montant maximal de virement dépassé");
        }

        if (versementPostDto.getMotif().length() < 0) {
            System.out.println("Motif vide");
            throw new MotifVideException("Motif vide");
        }

        cmpteBeneficiaire.setSolde(cmpteBeneficiaire.getSolde().add(versementPostDto.getMontantVersement()));
        compteRepository.save(cmpteBeneficiaire);
        Versement versement = versementMapper.versementPostDtoToVersement(versementPostDto);
        versementRepository.save(versement);

        auditService.auditVirement("Virement depuis " + versementPostDto.getNom_prenom_emetteur() + " vers "
                + versementPostDto.getRibCompteBeneficiaire() + " d'un montant de "
                + versementPostDto.getMontantVersement().toString());

        return versementMapper.versementToVersementGetDto(versement);
    }
}
