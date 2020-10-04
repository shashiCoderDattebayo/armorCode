package com.armorcode.secureearth.repository;

import com.armorcode.secureearth.model.IngestionToken;
import com.armorcode.secureearth.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngestionTokenRepository extends JpaRepository<IngestionToken, Long> {

    Optional<IngestionToken> findByToken(String token);

    Boolean existsByToken(String token);

    List<IngestionToken> findAllByTenantId(Long id);
}
