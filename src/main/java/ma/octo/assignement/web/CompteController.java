package ma.octo.assignement.web;

import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok()
                .body(compteService.getAllComptes());
    }
}
