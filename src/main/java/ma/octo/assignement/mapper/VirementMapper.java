package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementGetDto;
import ma.octo.assignement.dto.VirementPostDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface VirementMapper {
    VirementGetDto virementToVirementGetDto(Virement virement);
    Virement virementGetDtoToVirement(VirementGetDto virementGetDto);
    VirementPostDto virementToVirementPostDto(Virement virement);
    Virement virementPostDtoToVirement(VirementPostDto virementPostDto);
}
