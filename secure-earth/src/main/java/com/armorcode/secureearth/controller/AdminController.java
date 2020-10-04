package com.armorcode.secureearth.controller;

import com.armorcode.secureearth.exception.BadRequestException;
import com.armorcode.secureearth.model.AuthProvider;
import com.armorcode.secureearth.model.Tenant;
import com.armorcode.secureearth.model.User;
import com.armorcode.secureearth.model.UserRole;
import com.armorcode.secureearth.payload.AdminCreateRequest;
import com.armorcode.secureearth.payload.TenantCreateRequest;
import com.armorcode.secureearth.repository.TenantRepository;
import com.armorcode.secureearth.repository.UserRepository;
import com.armorcode.secureearth.security.CurrentUser;
import com.armorcode.secureearth.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    @PreAuthorize("hasRole('SUPER_USER') or hasRole('ADMIN')")
    public User addAdmin(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody AdminCreateRequest adminCreateRequest) {
        if(userRepository.existsByEmail(adminCreateRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        Optional<Tenant> tenantOptional = tenantRepository.findByName(adminCreateRequest.getTenantName());

        if(!tenantOptional.isPresent()) {
            throw new BadRequestException("Given Tenant does not exist");
        }

        User user = new User();
        user.setEmail(adminCreateRequest.getEmail());
        user.setProvider(AuthProvider.NONE);
        user.setUserRole(UserRole.ADMIN);
        user.setTenant(tenantOptional.get());
        return userRepository.save(user);
    }
}
