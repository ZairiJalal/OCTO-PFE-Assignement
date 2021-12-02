package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteDto {

    private String nrCompte;
    private String rib;
    private BigDecimal solde;
    private String usernameUtilisateur;

}
