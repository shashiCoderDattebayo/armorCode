package com.armorcode.secureearth.payload;

import com.armorcode.secureearth.model.UserRole;

import java.util.List;
import java.util.Map;

public class UserCreateRequest {
    @RoleUsers
    private Map<UserRole, List<String>> users;

    public Map<UserRole, List<String>> getUsers() {
        return users;
    }

    public void setUsers(Map<UserRole, List<String>> users) {
        this.users = users;
    }
}
