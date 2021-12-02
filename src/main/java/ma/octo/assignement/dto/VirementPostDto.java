package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirementPostDto {

    private String nrCompteEmetteur;
    private String nrCompteBeneficiaire;
    private String motif;
    private BigDecimal montantVirement;

}
