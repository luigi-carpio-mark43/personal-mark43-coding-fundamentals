package com.mark43.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReportFacadeTest {

    private ReportFacade facade;

    @BeforeEach
    void setUp() {
        facade = new ReportFacade();
    }

    private static User userWith(String... abilities) {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, abilities);
        return new User(42L, "Bob", set);
    }

    @Test
    void userWithSealAbilityCanSealReport() {
        User user = userWith(Abilities.SEAL_REPORT);

        Long result = facade.sealReport(user, 99L);

        assertEquals(99L, result);
        assertEquals(1, facade.getSealCallCount());
    }

    @Test
    void userWithoutSealAbilityThrows() {
        User user = userWith(Abilities.VIEW_REPORTS);

        assertThrows(PermissionDeniedException.class, () -> facade.sealReport(user, 99L));
    }

    @Test
    void failedPermissionCheckDoesNotIncrementSealCount() {
        User user = userWith(Abilities.VIEW_REPORTS);

        assertThrows(PermissionDeniedException.class, () -> facade.sealReport(user, 99L));

        assertEquals(0, facade.getSealCallCount(),
                "Permission check must happen BEFORE the seal action - seal count should be 0 when denied");
    }

    @Test
    void nullUserThrows() {
        assertThrows(PermissionDeniedException.class, () -> facade.sealReport(null, 99L));
    }
}
