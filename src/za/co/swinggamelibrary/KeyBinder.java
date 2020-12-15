/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author dkrou
 */
public class KeyBinder {

    private final JComponent container;
    private final int inputMap;
    public static final int WHEN_IN_FOCUSED_WINDOW = JComponent.WHEN_IN_FOCUSED_WINDOW,
            WHEN_FOCUSED = JComponent.WHEN_FOCUSED,
            WHEN_ANCESTOR_OF_FOCUSED_COMPONENT = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;

    public KeyBinder(JComponent container, int inputMap) {
        this.container = container;
        this.inputMap = inputMap;
    }

    public static void putKeyBinding(JComponent container, int inputMap, int key, boolean onRelease, Consumer<ActionEvent> keybindingAction, String description) {
        container.getInputMap(inputMap).put(KeyStroke.getKeyStroke(key, 0, onRelease), description);
        container.getActionMap().put(description, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keybindingAction.accept(e);
            }
        });
    }

    public void addKeyBinding(int key, boolean onRelease, Consumer<ActionEvent> keybindingAction, String description) {
        putKeyBinding(container, inputMap, key, onRelease, keybindingAction, description);
    }

    public void addKeyBindingOnPress(int key, Consumer<ActionEvent> keybindingAction, String description) {
        putKeyBinding(container, inputMap, key, false, keybindingAction, description);
    }

    public void addKeyBindingOnRelease(int key, Consumer<ActionEvent> keybindingAction, String description) {
        putKeyBinding(container, inputMap, key, true, keybindingAction, description);
    }

    public void addKeyBindingOnPressAndRelease(int key, Consumer<ActionEvent> onPressAction, String onPressDesc, Consumer<ActionEvent> onReleaseAction, String onReleaseDesc) {
        putKeyBinding(container, inputMap, key, false, onPressAction, onPressDesc);
        putKeyBinding(container, inputMap, key, true, onReleaseAction, onReleaseDesc);
    }

    public static void putKeyBindingOnPress(JComponent container, int inputMap, int key, Consumer<ActionEvent> keybindingAction, String description) {
        container.getInputMap(inputMap).put(KeyStroke.getKeyStroke(key, 0, false), description);
        container.getActionMap().put(description, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keybindingAction.accept(e);
            }
        });
    }

    public static void putKeyBindingOnRelease(JComponent container, int inputMap, int key, Consumer<ActionEvent> keybindingAction, String description) {
        container.getInputMap(inputMap).put(KeyStroke.getKeyStroke(key, 0, true), description);
        container.getActionMap().put(description, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keybindingAction.accept(e);
            }
        });
    }

    public static void putKeyBindingOnPressAndRelease(JComponent container, int inputMap, int key, Consumer<ActionEvent> onPressAction, String onPressDesc, Consumer<ActionEvent> onReleaseAction, String onReleaseDesc) {
        putKeyBinding(container, inputMap, key, false, onPressAction, onPressDesc);
        putKeyBinding(container, inputMap, key, true, onReleaseAction, onReleaseDesc);
    }
}
