package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface UtilisateurMapper {
    UtilisateurDto utilisateurToUtilisateurDto(Utilisateur utilisateur);
    Utilisateur utilisateurDtoToUtilisateur(UtilisateurDto utilisateurDto);
}
