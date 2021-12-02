package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersementPostDto {

    private BigDecimal montantVersement;
    private String nom_prenom_emetteur;
    private String ribCompteBeneficiaire;
    private String motif;
}