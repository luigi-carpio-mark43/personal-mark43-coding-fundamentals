package com.mark43.practice;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PermissionCheckerTest {

    private static User userWith(String... abilities) {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, abilities);
        return new User(1L, "Alice", set);
    }

    @Test
    void userWithAbilityPassesCleanly() {
        User user = userWith(Abilities.VIEW_REPORTS);
        assertDoesNotThrow(() -> PermissionChecker.ensurePermission(user, Abilities.VIEW_REPORTS));
    }

    @Test
    void userMissingAbilityThrowsPermissionDenied() {
        User user = userWith(Abilities.VIEW_REPORTS);
        PermissionDeniedException ex = assertThrows(PermissionDeniedException.class,
                () -> PermissionChecker.ensurePermission(user, Abilities.SEAL_REPORT));
        assertEquals("User Alice is missing ability: SEAL_REPORT", ex.getMessage());
    }

    @Test
    void userWithNoAbilitiesThrowsPermissionDenied() {
        User user = userWith();
        PermissionDeniedException ex = assertThrows(PermissionDeniedException.class,
                () -> PermissionChecker.ensurePermission(user, Abilities.VIEW_REPORTS));
        assertEquals("User Alice is missing ability: VIEW_REPORTS", ex.getMessage());
    }

    @Test
    void nullUserThrowsPermissionDenied() {
        PermissionDeniedException ex = assertThrows(PermissionDeniedException.class,
                () -> PermissionChecker.ensurePermission(null, Abilities.VIEW_REPORTS));
        assertEquals("user is required", ex.getMessage());
    }

    @Test
    void nullAbilityThrowsIllegalArgument() {
        User user = userWith(Abilities.VIEW_REPORTS);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PermissionChecker.ensurePermission(user, null));
        assertEquals("ability is required", ex.getMessage());
    }

    @Test
    void emptyAbilityThrowsIllegalArgument() {
        User user = userWith(Abilities.VIEW_REPORTS);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> PermissionChecker.ensurePermission(user, ""));
        assertEquals("ability is required", ex.getMessage());
    }
}
