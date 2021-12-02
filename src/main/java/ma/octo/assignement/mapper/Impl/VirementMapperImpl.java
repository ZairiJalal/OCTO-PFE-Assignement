package ma.octo.assignement.mapper.Impl;

import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementGetDto;
import ma.octo.assignement.dto.VirementPostDto;
import ma.octo.assignement.mapper.VirementMapper;
import ma.octo.assignement.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class VirementMapperImpl implements VirementMapper {
    @Autowired
    CompteRepository compteRepository;


    @Override
    public VirementGetDto virementToVirementGetDto(Virement virement) {
        if ( virement == null ) return null;

        VirementGetDto virementGetDto = new VirementGetDto();
        virementGetDto.setNrCompteEmetteur(virement.getCompteEmetteur().getNrCompte());
        virementGetDto.setNrCompteBeneficiaire(virement.getCompteBeneficiaire().getNrCompte());
        virementGetDto.setMontantVirement(virement.getMontantVirement());
        virementGetDto.setDate(virement.getDateExecution());
        virementGetDto.setMotif(virement.getMotifVirement());

        return virementGetDto;
    }

    @Override
    public Virement virementGetDtoToVirement(VirementGetDto virementGetDto) {
        if ( virementGetDto == null ) {
            return null;
        }
        Virement virement = new Virement();

        virement.setMontantVirement(virementGetDto.getMontantVirement());
        virement.setMotifVirement(virementGetDto.getMotif());
        virement.setCompteEmetteur(compteRepository.findByNrCompte(
                virementGetDto.getNrCompteEmetteur()
        ));
        virement.setCompteBeneficiaire(compteRepository.findByNrCompte(
                virementGetDto.getNrCompteBeneficiaire()
        ));
        virement.setDateExecution(new Date());

        return virement;
    }

    @Override
    public VirementPostDto virementToVirementPostDto(Virement virement) {
        if ( virement == null ) return null;

        VirementPostDto virementPostDto = new VirementPostDto();
        virementPostDto.setNrCompteEmetteur(virement.getCompteEmetteur().getNrCompte());
        virementPostDto.setNrCompteBeneficiaire(virement.getCompteBeneficiaire().getNrCompte());
        virementPostDto.setMontantVirement(virement.getMontantVirement());
        virementPostDto.setMotif(virement.getMotifVirement());

        return virementPostDto;
    }

    @Override
    public Virement virementPostDtoToVirement(VirementPostDto virementPostDto) {
        if ( virementPostDto == null ) {
            return null;
        }
        Virement virement = new Virement();

        virement.setMontantVirement(virementPostDto.getMontantVirement());
        virement.setMotifVirement(virementPostDto.getMotif());
        virement.setCompteEmetteur(compteRepository.findByNrCompte(
                virementPostDto.getNrCompteEmetteur()
        ));
        virement.setCompteBeneficiaire(compteRepository.findByNrCompte(
                virementPostDto.getNrCompteBeneficiaire()
        ));
        virement.setDateExecution(new Date());

        return virement;
    }
}
