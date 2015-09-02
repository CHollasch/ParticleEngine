package me.hollasch.particles.options.declared;

import me.hollasch.particles.options.SimpleJOption;
import me.hollasch.particles.options.UpdateEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropdownOption<V> extends SimpleJOption<JComboBox<V>, V> {

    private V[] options;

    public DropdownOption(V[] options, V initialValue, String name, UpdateEvent<V> onUpdate) {
        super(initialValue, name, onUpdate);

        this.options = options;
    }

    @Override
    public JComboBox<V> createComponent() {
        final JComboBox<V> comboBox = new JComboBox<V>();
        comboBox.setName(getDescription());

        for (int i = 0; i < options.length; ++i) {
            comboBox.addItem(options[i]);

            if (options[i].equals(active)) {
                comboBox.setSelectedIndex(i);
            }
        }

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeOptionValue((V) comboBox.getSelectedItem());
            }
        });

        return comboBox;
    }
}
