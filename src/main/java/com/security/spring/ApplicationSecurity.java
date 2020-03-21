package com.security.spring;

import com.security.auth.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.security.spring.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;
    private final ApplicationUserService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .authorizeRequests()
                .antMatchers("/", "/css/*", "/js/*", "index").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.DELETE, "/manage/api/**").hasAuthority(COURES_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/manage/api/**").hasAuthority(COURES_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/manage/api/**").hasAuthority(COURES_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/manage/api/**").hasAnyRole(ADMIN.name(), MIDDLE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("somethingVerySecured")
                .and()
                .logout().logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/pllogout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(service);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails std = User.builder()
//                .username("syed")
//                .password(encoder.encode("syed"))
//                //.roles(STUDENT.name()) //ROLE_STUDENT
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                //.roles(ADMIN.name()) //ROLE_ADMIN
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//        UserDetails middle = User.builder()
//                .username("middle")
//                .password(encoder.encode("middle"))
//                //.roles(MIDDLE.name()) //ROLE_MIDDLE
//                .authorities(MIDDLE.getGrantedAuthorities())
//                .build();

//        return new InMemoryUserDetailsManager(
//                std,
//                admin,
//                middle
//        );
//    }
}
