package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersementGetDto {

    private BigDecimal montantVersement;
    private Date date;
    private String nom_prenom_emetteur;
    private String ribCompteBeneficiaire;
    private String motif;
}
