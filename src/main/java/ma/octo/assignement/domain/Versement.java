package ma.octo.assignement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "VERSEMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Versement extends Operation {
  @Column
  private String nom_prenom_emetteur;
}
