package com.security.auth;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.security.spring.ApplicationUserRole.*;

@Repository("fake")
@RequiredArgsConstructor
public class FakeApplicationUserDaoServices implements ApplicationDAO {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        "syed",
                        passwordEncoder.encode("syed"),
                        true,
                        true,
                        true,
                        true
                ), new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        "admin",
                        passwordEncoder.encode("admin"),
                        true,
                        true,
                        true,
                        true
                ), new ApplicationUser(
                        MIDDLE.getGrantedAuthorities(),
                        "middle",
                        passwordEncoder.encode("middle"),
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
