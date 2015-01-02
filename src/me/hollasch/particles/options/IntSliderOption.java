package me.hollasch.particles.options;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class IntSliderOption implements Source<JSlider> {

    private int low;
    private int high;

    private JSlider slider;

    public IntSliderOption(int low, int high, int initial, String name, final UpdateEvent<Integer> onUpdate) {
        slider = new JSlider(low, high, initial);
        slider.setMajorTickSpacing((high - low) / 5);
        slider.setMinorTickSpacing((high - low) / 10);
        slider.setPaintTicks(true);

        Hashtable label = new Hashtable();
        label.put((high - low) / 2, new JLabel(name));

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

    public int getLowValue() {
        return low;
    }

    public int getHighValue() {
        return high;
    }
}
