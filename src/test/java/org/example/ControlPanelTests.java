package org.example;

import net.jqwik.api.*;
import org.example.util.ControlPanel;
import org.junit.jupiter.api.Assertions;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControlPanelTests {


    @Provide
    Arbitrary<Integer> invalidChoices() {
        return Arbitraries.integers()
                .filter(i -> i < 1 || i > 4);
    }

    @Property
    void captureInvalidChoice(@ForAll("invalidChoices") int value) {
        assertThrows(InputMismatchException.class, () -> ControlPanel.handleChoice(value));
    }

}