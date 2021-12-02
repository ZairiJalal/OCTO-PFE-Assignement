package ma.octo.assignement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPTE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 16, unique = true)
  private String nrCompte;

  @Column(unique = true)
  private String rib;

  @Column(precision = 16, scale = 2)
  private BigDecimal solde;

  @ManyToOne()
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;

  @OneToMany(mappedBy = "compteEmetteur", cascade = CascadeType.ALL)
  private List<Virement> virementsEmetteur = new ArrayList<>();

  @OneToMany(mappedBy = "compteBeneficiaire", cascade = CascadeType.ALL)
  private List<Virement> virementsBeneficiaire = new ArrayList<>();

  @OneToMany(mappedBy = "compteBeneficiaire", cascade = CascadeType.ALL)
  private List<Versement> versements = new ArrayList<>();
}
