package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteMapper {
    CompteDto compteToCompteDto(Compte compte);
    Compte compteDtoToCompte(CompteDto compteDto);
}
