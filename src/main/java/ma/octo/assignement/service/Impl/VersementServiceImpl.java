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
    public static final int MONTANT_MINIMAL = 10;

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

        if (nom_prenom_emetteur == null || nom_prenom_emetteur.length() == 0) {
            throw new TransactionException("nom & prenom de emetteur est vide");
        }

        if (cmpteBeneficiaire == null) {
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (versementPostDto.getMontantVersement().equals(null) || versementPostDto.getMontantVersement().intValue() == 0) {
            throw new TransactionException("Montant vide");
        } else if (versementPostDto.getMontantVersement().intValue() < MONTANT_MINIMAL) {
            throw new MontantMinNonAtteintExeption("Montant minimal de virement non atteint: "+MONTANT_MINIMAL);
        } else if (versementPostDto.getMontantVersement().intValue() > MONTANT_MAXIMAL) {
            throw new MontantMaxDepasseException("Montant maximal de virement dépassé: "+MONTANT_MAXIMAL);
        }

        if (versementPostDto.getMotif().length() == 0 || versementPostDto.getMotif().equals(null)) {
            throw new MotifVideException("Motif vide");
        }

        cmpteBeneficiaire.setSolde(cmpteBeneficiaire.getSolde().add(versementPostDto.getMontantVersement()));
        compteRepository.save(cmpteBeneficiaire);
        Versement versement = versementMapper.versementPostDtoToVersement(versementPostDto);
        versement = versementRepository.save(versement);

        auditService.auditVirement("Virement depuis " + versement.getNom_prenom_emetteur() + " vers "
                + versement.getCompteBeneficiaire().getRib()+ " d'un montant de "
                + versement.getMontant().toString());

        return versementMapper.versementToVersementGetDto(versement);
    }
}
