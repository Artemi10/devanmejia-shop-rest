package devanmejia.security.user;

import java.util.Arrays;

public enum UserRole {
    ROLE_ADMIN,
    ROLE_CLIENT,
    ROLE_UNAUTH_USER,
    ROLE_PASSWORD_RESET,
    ROLE_RESET_ALLOWED;

    public static String[] names() {
        return Arrays.stream(UserRole.values()).map(Enum::name).toArray(String[]::new);
    }

}
