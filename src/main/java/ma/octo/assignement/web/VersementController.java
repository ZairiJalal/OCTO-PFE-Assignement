package ma.octo.assignement.web;

import ma.octo.assignement.dto.VersementGetDto;
import ma.octo.assignement.dto.VersementPostDto;
import ma.octo.assignement.dto.VirementGetDto;
import ma.octo.assignement.exceptions.*;
import ma.octo.assignement.service.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/versements")
public class VersementController {

    @Autowired
    VersementService versementService;


    @GetMapping("/lister_versements")
    public ResponseEntity<List<VersementGetDto>> loadAll() {
        List<VersementGetDto> versementsDto = versementService.getVersements();
        return ResponseEntity.status((versementsDto == null || versementsDto.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(versementsDto);
    }

    @PostMapping("/executerVersements")
    public ResponseEntity<VersementGetDto> createTransaction(
            @RequestBody VersementPostDto versementPostDto
    ) throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException, MotifVideException, MontantMinNonAtteintExeption, MontantMaxDepasseException {
        return new ResponseEntity<>(versementService.addVersement(versementPostDto), HttpStatus.CREATED);
    }
}
