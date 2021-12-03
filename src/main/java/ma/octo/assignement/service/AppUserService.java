package ma.octo.assignement.service;

import ma.octo.assignement.domain.AppRole;
import ma.octo.assignement.domain.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username,String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}

