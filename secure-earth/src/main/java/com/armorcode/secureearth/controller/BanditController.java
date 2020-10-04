package com.armorcode.secureearth.controller;

import com.armorcode.secureearth.exception.BadRequestException;
import com.armorcode.secureearth.model.BanditFile;
import com.armorcode.secureearth.model.BanditResult;
import com.armorcode.secureearth.model.Finding;
import com.armorcode.secureearth.model.IngestionToken;
import com.armorcode.secureearth.repository.FindingRepository;
import com.armorcode.secureearth.repository.IngestionTokenRepository;
import com.armorcode.secureearth.repository.TenantRepository;
import com.armorcode.secureearth.repository.UserRepository;
import com.armorcode.secureearth.security.CurrentUser;
import com.armorcode.secureearth.security.UserPrincipal;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bandit")
public class BanditController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private IngestionTokenRepository ingestionTokenRepository;

    @Autowired
    private FindingRepository findingRepository;

    @PostMapping()
    public ResponseEntity<?> addFindings(@RequestHeader("ingestion-token") String token, @NotNull @Valid @RequestBody BanditFile banditFile) {
        Optional<IngestionToken> ingestionTokenOptional = ingestionTokenRepository.findByToken(token);
        if (!ingestionTokenOptional.isPresent()) {
            throw new BadRequestException("Ingestion token is not valid");
        }
        List<Finding> findings = Lists.newArrayList();
        for (BanditResult banditResult : banditFile.getResults()) {
            Finding finding = new Finding();
            finding.setCode(banditResult.getCode());
            finding.setFilename(banditResult.getFilename());
            finding.setIssue_confidence(banditResult.getIssue_confidence());
            finding.setIssue_severity(banditResult.getIssue_severity());
            finding.setIssue_text(banditResult.getIssue_text());
            finding.setMore_info(banditResult.getMore_info());
            finding.setTest_id(banditResult.getTest_id());
            finding.setTest_name(banditResult.getTest_name());
            finding.setLine_number(banditResult.getLine_number());
            finding.setIngestionToken(ingestionTokenOptional.get());
            findings.add(finding);
        }
        findingRepository.saveAll(findings);
        return ResponseEntity.ok("Successfully inserted all the findings.");
    }
}
