package com.wyona.multitenantservice.webapp.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import static org.springframework.http.HttpMethod.*;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.LinkedHashMap;

/**
 * See https://www.baeldung.com/spring-security-expressions
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    /**
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configure / set access permissions ...");

        http.addFilterBefore(corsFilter(), SessionManagementFilter.class); // https://stackoverflow.com/questions/40286549/spring-boot-security-cors

        // TODO: Reconsider enabling CSRF, whereas see https://portswigger.net/web-security/csrf
        http.csrf().disable(); // INFO: Disabled, because otherwise one cannot use protected interfaces via Swagger even when signed in via Swagger, whereas see https://stackoverflow.com/questions/38004035/could-not-verify-the-provided-csrf-token-because-your-session-was-not-found-in-s

        //http.authorizeRequests().anyRequest().permitAll();

        http.formLogin().disable();

        http.authorizeRequests()
                .antMatchers(GET, "/api/v1/user/profile").authenticated()
                .antMatchers(GET, "/api/v1/submitQuestionToExpert").hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/configuration").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/configuration/context").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/configuration/context/*").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/summary/current").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/dataset").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/questions/*").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/question/resubmitted/*").hasRole(ADMIN_ROLE)
                .antMatchers(PUT, "/api/v1/question/resubmitted/*").hasRole(ADMIN_ROLE)
                .antMatchers(DELETE, "/api/v1/question/resubmitted/*").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/question/resubmitted/*/sendAnswer").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/question/resubmitted/*/train").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/api/v1/question/resubmitted/*/forget").hasRole(ADMIN_ROLE)
                .antMatchers(POST, "/tmp-test/push-notification/topic").hasRole(ADMIN_ROLE)
                .antMatchers(POST, "/tmp-test/push-notification/token").hasRole(ADMIN_ROLE)
                .antMatchers(POST, "/tmp-test/push-notification/data").hasRole(ADMIN_ROLE)
                .antMatchers(GET, "/tmp-test/push-notification/sample").hasRole(ADMIN_ROLE)
                //.antMatchers(GET, "/api/v1/question/resubmitted/*/rateAnswer").permitAll()
                //.antMatchers(GET, "/api/v1/question/resubmitted/*/answer").permitAll()
                .antMatchers(GET, "/**").permitAll()
                .antMatchers(PUT, "/**").permitAll()
                .antMatchers(POST, "/**").permitAll()
                .and()
                .logout() // https://www.baeldung.com/spring-security-logout
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);
    }

    /**
     *
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Use in memory authentication provider ...");
        User[] users = new User[1];
        users[0] = new User();
        for (int i = 0; i < users.length; i++) {
            auth.inMemoryAuthentication().withUser(users[i].getUsername()).password("{noop}" + users[i].getPlaintextPassword()).roles(users[i].getRole());
        }
    }
}

/**
 *
 */
class User {

    /**
     *
     */
    public User() {
    }

    /**
     *
     */
    public String getUsername() {
        return "michael";
    }

    /**
     *
     */
    public String getPlaintextPassword() {
        return "1234";
    }

    /**
     *
     */
    public String getRole() {
        return "USER";
    }
}
