package me.hollasch.particles.options;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class NumberSliderOption implements Source<JSlider> {

    private JSlider slider;

    public NumberSliderOption(Integer low, Integer high, Integer initial, String name, final UpdateEvent<Integer> onUpdate) {
        slider = new JSlider(low, high, initial);
        slider.setMajorTickSpacing((high.intValue() - low.intValue()) / 10);
        slider.setPaintTicks(true);

        Hashtable label = new Hashtable();
        label.put((high.intValue() - low.intValue()) / 2, new JLabel(name));

        slider.setLabelTable(label);
        slider.setPaintLabels(true);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                onUpdate.onUpdate(((JSlider)e.getSource()).getValue());
            }
        });

        onUpdate.onUpdate(initial);
    }

    public JSlider get() {
        return slider;
    }
}
