package com.workintech.spring_authentication_authorization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="app_user",schema = "fsweb")

public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="full_name")
    private String fullName;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;

    //Unidirectional
    @ManyToMany(fetch=FetchType.EAGER)//defaultu Lazy  sistemsel hatalardan korumak için User'i aldığımızda rolleri getirmek için eagerr kullanıyoruz
    @JoinTable(name="app_user_role", schema="fsweb",
    joinColumns=@JoinColumn(name="app_user_id"),
    inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> authorities=new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    //Aşağıdaki dört metot Kullanıcıyı sistemden atmak için kullanılır biri false olursa kullanıcı içeri giremez
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
