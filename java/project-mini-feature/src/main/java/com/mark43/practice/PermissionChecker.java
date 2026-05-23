package com.mark43.practice;

/**
 * Pre-built. Do not edit.
 */
public class PermissionChecker {

    public static void ensurePermission(User user, String ability) {
        if (user == null) {
            throw new PermissionDeniedException("user is required");
        }
        if (ability == null || ability.isEmpty()) {
            throw new IllegalArgumentException("ability is required");
        }
        if (!user.hasAbility(ability)) {
            throw new PermissionDeniedException(
                    "User " + user.getName() + " is missing ability: " + ability);
        }
    }
}
