package com.armorcode.secureearth.model;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.armorcode.secureearth.model.UserAuthority.ALL_TENANTS_AUTHOURITY;
import static com.armorcode.secureearth.model.UserAuthority.WITHIN_TENANT_AUTHOURITY;

public enum UserRole {
    SUPER_USER() {
        @Override
        public Set<UserAuthority> getAuthorities() {
            return Sets.newHashSet(ALL_TENANTS_AUTHOURITY);
        }
    },
    ADMIN() {
        @Override
        public Set<UserAuthority> getAuthorities() {
            return Sets.newHashSet(WITHIN_TENANT_AUTHOURITY);
        }
    },
    SECURITY_ENGINEER() {
        @Override
        public Set<UserAuthority> getAuthorities() {
            return Sets.newHashSet();
        }
    },
    DEVELOPER() {
        @Override
        public Set<UserAuthority> getAuthorities() {
            return Sets.newHashSet();
        }
    };

    public abstract Set<UserAuthority> getAuthorities();
}
