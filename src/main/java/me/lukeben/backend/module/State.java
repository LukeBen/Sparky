package me.lukeben.backend.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Holds the state of our module.
 */
@AllArgsConstructor
@Getter
public enum State {
    ENABLED,
    DISABLED
}
