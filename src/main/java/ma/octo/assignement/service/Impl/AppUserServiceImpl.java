package ma.octo.assignement.service.Impl;

import ma.octo.assignement.domain.AppRole;
import ma.octo.assignement.domain.AppUser;
import ma.octo.assignement.repository.AppRoleRepository;
import ma.octo.assignement.repository.AppUserRepository;
import ma.octo.assignement.service.AppUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    public AppUserServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder)
    {
        this.appUserRepository = appUserRepository; this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public AppUser addNewUser(AppUser appUser) {
        String pw=appUser.getPassword(); appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }
    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }
    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username); AppRole appRole=appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }
    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
