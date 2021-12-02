package ma.octo.assignement.web;

import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("lister_utilisateurs")
    ResponseEntity<List<UtilisateurDto>> loadAllUtilisateur() {
        return ResponseEntity.ok()
                .body(utilisateurService.getAllUtilisateurs());
    }
}
