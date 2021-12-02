package ma.octo.assignement.service;

import ma.octo.assignement.dto.VersementGetDto;
import ma.octo.assignement.dto.VersementPostDto;
import ma.octo.assignement.exceptions.*;

import java.util.List;

public interface VersementService {
    List<VersementGetDto> getVersements();
    VersementGetDto addVersement(VersementPostDto versementPostDto) throws CompteNonExistantException, TransactionException, MontantMinNonAtteintExeption, MontantMaxDepasseException, MotifVideException;
}
