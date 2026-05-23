package com.mark43.practice;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Pre-built. Do not edit.
 */
public class User {

    private final Long id;
    private final String name;
    private final Set<String> abilities;

    public User(Long id, String name, Set<String> abilities) {
        this.id = id;
        this.name = name;
        this.abilities = (abilities == null) ? Collections.emptySet() : new HashSet<>(abilities);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<String> getAbilities() { return Collections.unmodifiableSet(abilities); }

    public boolean hasAbility(String ability) {
        return abilities.contains(ability);
    }
}
