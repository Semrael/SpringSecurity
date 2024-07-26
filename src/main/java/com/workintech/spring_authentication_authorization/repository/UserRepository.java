package com.workintech.spring_authentication_authorization.repository;

import com.workintech.spring_authentication_authorization.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser,Long> {
   @Query("Select u from ApplicationUser u Where u.email = :email")//JPQL yazarken tabloyu değil entity mizi kullanıyoruz //Native query de kullanabiliriz
    Optional<ApplicationUser> findUserByEmail(String email);
}
