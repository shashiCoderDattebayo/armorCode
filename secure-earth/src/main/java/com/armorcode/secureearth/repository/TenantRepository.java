package com.armorcode.secureearth.repository;

import com.armorcode.secureearth.model.Tenant;
import com.armorcode.secureearth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByName(String name);

    Boolean existsByName(String name);
}
