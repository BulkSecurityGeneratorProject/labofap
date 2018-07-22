package fr.labofap.api.security;

import fr.labofap.api.config.Constants;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();

        System.out.println("Current Auditor : "+ userName);
        return userName != null ? userName : Constants.SYSTEM_ACCOUNT;
    }
}
