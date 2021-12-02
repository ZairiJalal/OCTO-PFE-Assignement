package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.dto.VersementGetDto;
import ma.octo.assignement.dto.VersementPostDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface VersementMapper {
    VersementGetDto versementToVersementGetDto(Versement versement);
    Versement versementGetDtoToVersement(VersementGetDto versementGetDto);
    VersementPostDto versementToVersementPostDto(Versement versement);
    Versement versementPostDtoToVersement(VersementPostDto versementPostDto);
}
