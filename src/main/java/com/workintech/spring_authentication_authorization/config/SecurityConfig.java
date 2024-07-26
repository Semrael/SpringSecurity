package com.workintech.spring_authentication_authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//csrf hatası=> cross site resource forgery=>kimlik çalma
//Authentication(Sisteme login olmak,Giriş yapmak) ve Authorization(Yetkilendirme-Rol based)
@Configuration

public class SecurityConfig {


    //Passwordu encode etmek için kullanılan bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Log-in'i database üstünden yapması için
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);

    }


    //Uygulamamızın security'sini(security nin kalbi) yönettiğimiz bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/welcome/**").permitAll();//welcome la başlayan her şeye izin ver
                    auth.requestMatchers("/auth/**").permitAll();//auth la başlayan her şeye izin ver
                    auth.requestMatchers("/admin/**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();//kalan her şey için authenticat ol
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
