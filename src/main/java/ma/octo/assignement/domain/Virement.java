package ma.octo.assignement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "VIREMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Virement extends Operation {
  @ManyToOne
  private Compte compteEmetteur;
}

