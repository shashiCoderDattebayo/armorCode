package com.armorcode.secureearth.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AdminCreateRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String tenantName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
