package com.armorcode.secureearth.repository;

import com.armorcode.secureearth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByTenantId(Long id);

    Boolean existsByEmail(String email);

}
