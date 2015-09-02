package me.hollasch.particles.options.declared;

import me.hollasch.particles.options.SimpleJOption;
import me.hollasch.particles.options.UpdateEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ToggleOption extends SimpleJOption<JCheckBox, Boolean> {

    public ToggleOption(boolean initial, String name, final UpdateEvent<Boolean> onUpdate) {
        super(initial, name, onUpdate);
    }

    @Override
    public JCheckBox createComponent() {
        final JCheckBox checkBox = new JCheckBox(getDescription());

        checkBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                changeOptionValue(checkBox.isSelected());
            }
        });

        return checkBox;
    }
}
