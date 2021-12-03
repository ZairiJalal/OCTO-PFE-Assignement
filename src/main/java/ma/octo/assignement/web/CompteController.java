package ma.octo.assignement.web;

import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/comptes")
public class CompteController {

    @Autowired
    CompteService compteService;

    @GetMapping("lister_comptes")
    ResponseEntity<List<CompteDto>> loadAllCompte() {
        List<CompteDto> ComptesDto = compteService.getAllComptes();
        return ResponseEntity.status((ComptesDto == null || ComptesDto.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(ComptesDto);
    }
}
