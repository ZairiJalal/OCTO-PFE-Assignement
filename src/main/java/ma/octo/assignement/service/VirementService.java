package ma.octo.assignement.service;

import ma.octo.assignement.dto.VirementGetDto;
import ma.octo.assignement.dto.VirementPostDto;
import ma.octo.assignement.exceptions.*;

import java.util.List;

public interface VirementService {
    List<VirementGetDto> getVirements();
    VirementGetDto addVirement(VirementPostDto virementPostDto) throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException, MotifVideException, MontantMinNonAtteintExeption, MontantMaxDepasseException;
}
