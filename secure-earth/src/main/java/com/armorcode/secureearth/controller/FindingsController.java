package com.armorcode.secureearth.controller;

import com.armorcode.secureearth.model.Finding;
import com.armorcode.secureearth.repository.FindingRepository;
import com.armorcode.secureearth.repository.IngestionTokenRepository;
import com.armorcode.secureearth.repository.TenantRepository;
import com.armorcode.secureearth.repository.UserRepository;
import com.armorcode.secureearth.security.CurrentUser;
import com.armorcode.secureearth.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/findings")
public class FindingsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private IngestionTokenRepository ingestionTokenRepository;

    @Autowired
    private FindingRepository findingRepository;

    @GetMapping()
    public List<Finding> getFindings(@CurrentUser UserPrincipal userPrincipal) {
        return findingRepository.findAllByIngestionTokenTenantId(userPrincipal.getTenantId());
    }
}
