package fr.labofap.api.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String PROTHESISTE = "ROLE_PROTHESISTE";

    public static final String DENTISTE = "ROLE_DENTISTE";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
