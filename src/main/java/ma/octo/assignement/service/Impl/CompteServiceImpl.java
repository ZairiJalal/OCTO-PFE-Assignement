package ma.octo.assignement.service.Impl;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CompteServiceImpl implements CompteService {

    @Autowired
    CompteRepository compteRepository;
    @Autowired
    CompteMapper compteMapper;

    @Override
    public List<CompteDto> getAllComptes() {
        List<Compte> comptes = compteRepository.findAll();
        List<CompteDto> comptesDto = new ArrayList<>();
        for(Compte compte : comptes){
            comptesDto.add(compteMapper.compteToCompteDto(compte));
        }
        return comptesDto;
    }

}
