package com.szwadronwilkowalfa.statystyki;

import com.szwadronwilkowalfa.statystyki.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PersonRepository personRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF handled by Vaadin
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                .logout().logoutSuccessUrl("/login/loggedout")
                .and()
                .authorizeRequests()
                .antMatchers("/VAADIN/**", "/HEARTBEAT/**", "/UIDL/**", "/resources/**"
                        , "/login", "/login**", "/login/**", "/manifest.json", "/icons/**", "/images/**",
                        // (development mode) static resources
                        "/frontend/**",
                        // (production mode) static resources
                        "/frontend-es5/**", "/frontend-es6/**",
                        "/h2-console"
                        ).permitAll()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                // deny other URLs until authenticated
                .anyRequest().fullyAuthenticated();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("admin")
                .password(encoder().encode("admin"))
                .roles("ADMIN").build());
        return manager;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}