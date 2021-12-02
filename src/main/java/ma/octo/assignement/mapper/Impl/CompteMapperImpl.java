package ma.octo.assignement.mapper.Impl;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompteMapperImpl implements CompteMapper {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public CompteDto compteToCompteDto(Compte compte) {
        if ( compte == null ) return null;

        CompteDto compteDto = new CompteDto();
        compteDto.setNrCompte(compte.getNrCompte());
        compteDto.setRib(compte.getRib());
        compteDto.setSolde(compte.getSolde());
        compteDto.setUsernameUtilisateur(compte.getUtilisateur().getUsername());

        return compteDto;
    }

    @Override
    public Compte compteDtoToCompte(CompteDto compteDto) {
        if ( compteDto == null ) return null;

        Compte compte = new Compte();
        compte.setNrCompte(compteDto.getNrCompte());
        compte.setRib(compteDto.getRib());
        compte.setSolde(compteDto.getSolde());
        compte.setUtilisateur(utilisateurRepository.findByUsername(compteDto.getUsernameUtilisateur()));

        return compte;
    }
}
