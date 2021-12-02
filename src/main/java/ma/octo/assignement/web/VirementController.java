package ma.octo.assignement.web;

import ma.octo.assignement.dto.VirementGetDto;
import ma.octo.assignement.dto.VirementPostDto;
import ma.octo.assignement.exceptions.*;
import ma.octo.assignement.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/virements")
class VirementController {

    @Autowired
    VirementService virementService;


    @GetMapping("lister_virements")
    ResponseEntity<List<VirementGetDto>> loadAll() {
        /*return ResponseEntity.ok()
                .body(virementService.getVirements());*/
        List<VirementGetDto> virementsDto = virementService.getVirements();
        return ResponseEntity.status((virementsDto == null || virementsDto.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(virementsDto);
    }

    @PostMapping("/executerVirements")
    public ResponseEntity<VirementGetDto> createTransaction(@RequestBody VirementPostDto virementPostDto)
    throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException, MotifVideException, MontantMinNonAtteintExeption, MontantMaxDepasseException {
        return new ResponseEntity<>(virementService.addVirement(virementPostDto), HttpStatus.CREATED);
    }
}
