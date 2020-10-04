package com.armorcode.secureearth.controller;

import com.armorcode.secureearth.model.Tenant;
import com.armorcode.secureearth.payload.LoginRequest;
import com.armorcode.secureearth.payload.TenantCreateRequest;
import com.armorcode.secureearth.repository.TenantRepository;
import com.armorcode.secureearth.security.CurrentUser;
import com.armorcode.secureearth.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @GetMapping()
    @PreAuthorize("hasRole('SUPER_USER')")
    public List<Tenant> getTenants(@CurrentUser UserPrincipal userPrincipal) {
        return tenantRepository.findAll();
    }

    @PostMapping()
    @PreAuthorize("hasRole('SUPER_USER')")
    public Tenant addTenant(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody TenantCreateRequest tenantCreateRequest) {
        Tenant tenant = new Tenant();
        tenant.setName(tenantCreateRequest.getName());
        tenant.setDetails(tenantCreateRequest.getDescription());
        Tenant save = tenantRepository.save(tenant);
        return save;
    }
}
