package com.security.auth;

import java.util.Optional;

public interface ApplicationDAO {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
