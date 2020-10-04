package com.armorcode.secureearth.payload;

import com.armorcode.secureearth.model.UserRole;
import org.apache.commons.collections4.MapUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

public class RoleUsersValidator implements ConstraintValidator<RoleUsers, Map<UserRole, List<String>>> {
    @Override
    public boolean isValid(Map<UserRole, List<String>> value, ConstraintValidatorContext context) {
        if(MapUtils.isEmpty(value))
            return false;
        for (Map.Entry<UserRole, List<String>> userRoleListEntry : value.entrySet()) {
            if (UserRole.SUPER_USER.equals(userRoleListEntry.getKey())) {
                return false;
            }
        }
        return true;
    }
}
