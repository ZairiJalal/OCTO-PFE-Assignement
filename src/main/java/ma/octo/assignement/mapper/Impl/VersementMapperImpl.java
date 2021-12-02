package ma.octo.assignement.mapper.Impl;

import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.dto.VersementGetDto;
import ma.octo.assignement.dto.VersementPostDto;
import ma.octo.assignement.mapper.VersementMapper;
import ma.octo.assignement.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class VersementMapperImpl implements VersementMapper {
    @Autowired
    CompteRepository compteRepository;

    @Override
    public VersementGetDto versementToVersementGetDto(Versement versement) {
        if ( versement == null ) return null;

        VersementGetDto versementGetDto = new VersementGetDto();
        versementGetDto.setNom_prenom_emetteur(versement.getNom_prenom_emetteur());
        versementGetDto.setRibCompteBeneficiaire(versement.getCompteBeneficiaire().getRib());
        versementGetDto.setMontantVersement(versement.getMontantVersement());
        versementGetDto.setDate(versement.getDateExecution());
        versementGetDto.setMotif(versement.getMotifVersement());

        return versementGetDto;
    }

    @Override
    public Versement versementGetDtoToVersement(VersementGetDto versementGetDto) {
        if ( versementGetDto == null ) {
            return null;
        }
        Versement versement = new Versement();

        versement.setMontantVersement(versementGetDto.getMontantVersement());
        versement.setMotifVersement(versementGetDto.getMotif());
        versement.setNom_prenom_emetteur(versementGetDto.getNom_prenom_emetteur());
        versement.setCompteBeneficiaire(compteRepository.findByRib(
                versementGetDto.getRibCompteBeneficiaire()
        ));
        versement.setDateExecution(new Date());

        return versement;
    }

    @Override
    public VersementPostDto versementToVersementPostDto(Versement versement) {
        if ( versement == null ) return null;

        VersementPostDto versementPostDto = new VersementPostDto();
        versementPostDto.setNom_prenom_emetteur(versement.getNom_prenom_emetteur());
        versementPostDto.setRibCompteBeneficiaire(versement.getCompteBeneficiaire().getRib());
        versementPostDto.setMontantVersement(versement.getMontantVersement());
        versementPostDto.setMotif(versement.getMotifVersement());

        return versementPostDto;
    }

    @Override
    public Versement versementPostDtoToVersement(VersementPostDto versementPostDto) {
        if ( versementPostDto == null ) {
            return null;
        }
        Versement versement = new Versement();

        versement.setMontantVersement(versementPostDto.getMontantVersement());
        versement.setMotifVersement(versementPostDto.getMotif());
        versement.setNom_prenom_emetteur(versementPostDto.getNom_prenom_emetteur());
        versement.setCompteBeneficiaire(compteRepository.findByRib(
                versementPostDto.getRibCompteBeneficiaire()
        ));

        return versement;
    }
}
