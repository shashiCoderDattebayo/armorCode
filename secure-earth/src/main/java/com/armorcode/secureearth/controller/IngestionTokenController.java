package com.armorcode.secureearth.controller;

import com.armorcode.secureearth.exception.BadRequestException;
import com.armorcode.secureearth.exception.ResourceNotFoundException;
import com.armorcode.secureearth.model.AuthProvider;
import com.armorcode.secureearth.model.IngestionToken;
import com.armorcode.secureearth.model.Tenant;
import com.armorcode.secureearth.model.User;
import com.armorcode.secureearth.model.UserRole;
import com.armorcode.secureearth.payload.IngestionTokenCreateRequest;
import com.armorcode.secureearth.payload.UserCreateRequest;
import com.armorcode.secureearth.repository.IngestionTokenRepository;
import com.armorcode.secureearth.repository.TenantRepository;
import com.armorcode.secureearth.repository.UserRepository;
import com.armorcode.secureearth.security.CurrentUser;
import com.armorcode.secureearth.security.TokenProvider;
import com.armorcode.secureearth.security.UserPrincipal;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/ingestionToken")
public class IngestionTokenController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private IngestionTokenRepository ingestionTokenRepository;

    @GetMapping()
    public List<User> getUsers(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findAllByTenantId(userPrincipal.getTenantId());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<IngestionToken> getTokens(@CurrentUser UserPrincipal userPrincipal) {
        return ingestionTokenRepository.findAllByTenantId(userPrincipal.getTenantId());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public IngestionToken createIngestionToken(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody IngestionTokenCreateRequest ingestionTokenCreateRequest) {
        String ingestionTokenValue = UUID.randomUUID().toString();
        Optional<Tenant> tenantOptional = tenantRepository.findById(userPrincipal.getTenantId());
        Optional<User> userOptional = userRepository.findById(userPrincipal.getId());
        if (!tenantOptional.isPresent() || !userOptional.isPresent()) {
            throw new BadRequestException("tenant or user does not exist for the logged in user.");
        }
        IngestionToken ingestionToken = new IngestionToken();
        ingestionToken.setDetails(ingestionTokenCreateRequest.getDescription());
        ingestionToken.setTenant(tenantOptional.get());
        ingestionToken.setUser(userOptional.get());
        ingestionToken.setToken(ingestionTokenValue);
        return ingestionTokenRepository.save(ingestionToken);
    }
}
