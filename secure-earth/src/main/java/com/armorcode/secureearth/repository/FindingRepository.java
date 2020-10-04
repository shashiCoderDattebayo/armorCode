package com.armorcode.secureearth.repository;

import com.armorcode.secureearth.model.Finding;
import com.armorcode.secureearth.model.IngestionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FindingRepository extends JpaRepository<Finding, Long> {
    List<Finding> findAllByIngestionTokenToken(String token);

    List<Finding> findAllByIngestionTokenTenantId(Long id);
}
