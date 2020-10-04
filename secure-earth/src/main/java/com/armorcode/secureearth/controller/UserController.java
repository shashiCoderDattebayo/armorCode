package com.armorcode.secureearth.controller;

import com.armorcode.secureearth.exception.BadRequestException;
import com.armorcode.secureearth.exception.ResourceNotFoundException;
import com.armorcode.secureearth.model.AuthProvider;
import com.armorcode.secureearth.model.Tenant;
import com.armorcode.secureearth.model.User;
import com.armorcode.secureearth.model.UserRole;
import com.armorcode.secureearth.payload.TenantCreateRequest;
import com.armorcode.secureearth.payload.UserCreateRequest;
import com.armorcode.secureearth.repository.TenantRepository;
import com.armorcode.secureearth.repository.UserRepository;
import com.armorcode.secureearth.security.CurrentUser;
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

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findAllByTenantId(userPrincipal.getTenantId());
    }

    @GetMapping("/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> addUsers(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody UserCreateRequest userCreateRequest) {
        Optional<Tenant> tenantOptional = tenantRepository.findById(userPrincipal.getTenantId());
        if (!tenantOptional.isPresent() || "PUBLIC".equals(tenantOptional.get().getName())) {
            throw new BadRequestException("The user is not valid");
        }
        List<User> users = Lists.newArrayList();
        for (Map.Entry<UserRole, List<String>> userRoleListEntry : userCreateRequest.getUsers().entrySet()) {
            UserRole userRole = userRoleListEntry.getKey();
            List<String> emails = userRoleListEntry.getValue();
            for (String email : emails) {
                if (userRepository.existsByEmail(email)) {
                    throw new BadRequestException("The user email already exists.");
                }
                User user = new User();
                user.setEmail(email);
                user.setProvider(AuthProvider.NONE);
                user.setUserRole(userRole);
                user.setTenant(tenantOptional.get());
                users.add(user);
            }
        }
        return userRepository.saveAll(users);
    }

}
