package ma.octo.assignement.mapper.Impl;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.mapper.UtilisateurMapper;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapperImpl implements UtilisateurMapper {
    @Override
    public UtilisateurDto utilisateurToUtilisateurDto(Utilisateur utilisateur) {
        if ( utilisateur == null ) return null;

        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setUsername(utilisateur.getUsername());
        utilisateurDto.setGender(utilisateur.getGender());
        utilisateurDto.setLastname(utilisateur.getLastname());
        utilisateurDto.setFirstname(utilisateur.getFirstname());
        utilisateurDto.setBirthdate(utilisateur.getBirthdate());

        return utilisateurDto;
    }

    @Override
    public Utilisateur utilisateurDtoToUtilisateur(UtilisateurDto utilisateurDto) {
        return null;
    }
}
