package ma.octo.assignement.service.Impl;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.mapper.UtilisateurMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurMapper utilisateurMapper;

    @Override
    public List<UtilisateurDto> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        List<UtilisateurDto> utilisateursDto = new ArrayList<>();
        for(Utilisateur utilisateur : utilisateurs){
            utilisateursDto.add(utilisateurMapper.utilisateurToUtilisateurDto(utilisateur));
        }
        return utilisateursDto;
    }
}
