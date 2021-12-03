package ma.octo.assignement.security;

import java.util.ArrayList;
import java.util.Collection;

import ma.octo.assignement.domain.AppUser;
import ma.octo.assignement.service.AppUserService;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.context.annotation.Bean;
import  org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import  org.springframework.security.authentication.AuthenticationManager;
import  org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import  org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import  org.springframework.security.core.GrantedAuthority;
import  org.springframework.security.core.userdetails.User;
import  org.springframework.security.core.userdetails.UserDetails;
import  org.springframework.security.core.userdetails.UserDetailsService;
import  org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

@EnableWebSecurity public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private AppUserService appUserService;

    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {

            @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser appUser = appUserService.loadUserByUsername(username);
                Collection<GrantedAuthority>  authorities= new ArrayList<>();
                appUser.getAppRoles().forEach(r->{
                    authorities.add(new SimpleGrantedAuthority(r.getRoleName())); });
                return new  User(appUser.getUsername(), appUser.getPassword(), authorities);
            }
        });
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/**").permitAll();
        //http.formLogin();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/users/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/roles/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/addRoleToUser/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}