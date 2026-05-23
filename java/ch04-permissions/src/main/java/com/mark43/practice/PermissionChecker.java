package com.mark43.practice;

/**
 * The permission check helper. Build the `ensurePermission` method.
 */
public class PermissionChecker {

    /**
     * Verify that the user has the given ability. Throws if not.
     *
     * Build this by following the TODOs.
     */
    public static void ensurePermission(User user, String ability) {
        // TODO 1: If user is null,
        //         throw new PermissionDeniedException("user is required")

        // TODO 2: If ability is null OR ability.isEmpty(),
        //         throw new IllegalArgumentException("ability is required")

        // TODO 3: If user.hasAbility(ability) is FALSE,
        //         throw new PermissionDeniedException(
        //             "User " + user.getName() + " is missing ability: " + ability
        //         )

        // TODO 4: If all checks pass, just return (no return value needed - method is void).
    }
}
