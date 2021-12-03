package ma.octo.assignement.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.octo.assignement.domain.AppRole;
import ma.octo.assignement.domain.AppUser;
import ma.octo.assignement.service.AppUserService;
import  org.springframework.web.bind.annotation.GetMapping;
import  org.springframework.web.bind.annotation.PostMapping;
import  org.springframework.web.bind.annotation.RequestBody;
import  org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.Data;

@RestController public class AppUserController {
    private AppUserService appUserService;
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping(path = "/users")
    public List<AppUser> appUsers() {
        return appUserService.listUsers();
    }

    @PostMapping(path = "/users")
    public AppUser saveUser(@RequestBody AppUser appUser) {
        return appUserService.addNewUser(appUser);
    }

    @PostMapping(path = "/roles")
    public AppRole saveRole(@RequestBody AppRole appRole) {
        return appUserService.addNewRole(appRole);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        appUserService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
    }


    @GetMapping(path ="/refreshToken")
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            try {
                String jwtRefreshToken = token.substring(7);
                Algorithm algorithm = Algorithm.HMAC256("myHMACPrivateKey");
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
                String username = decodedJWT.getSubject();
                AppUser appUser = appUserService.loadUserByUsername(username);
                String jwtAccessToken = JWT
                        .create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 2 * 60 * 1000))
                        .withIssuer(request.getRequestURL()
                                .toString())
                        .withClaim("roles", appUser
                                .getAppRoles()
                                .stream().map(e ->e.
                                        getRoleName()).
                                collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> accessToken = new HashMap<>();
                accessToken.put("Access_Token", jwtAccessToken);
                accessToken.put("Refresh_Token", jwtRefreshToken);
                return accessToken;
            }
            catch (TokenExpiredException e) {
                response.setHeader("Error-Message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } throw new RuntimeException("Bad Refresh Token");
    }

    @Data
    class RoleUserForm{
        private String username;
        private String roleName; }
}
