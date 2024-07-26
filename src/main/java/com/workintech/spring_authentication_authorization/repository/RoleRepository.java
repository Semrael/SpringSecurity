package com.workintech.spring_authentication_authorization.repository;

import com.workintech.spring_authentication_authorization.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("Select r From Role r Where r.authority = :authority")
   Optional<Role> findByAuthority(String authority);
}
