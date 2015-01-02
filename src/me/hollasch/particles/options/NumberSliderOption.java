package me.hollasch.particles.options;

import me.hollasch.particles.debug.OptionDebugManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class NumberSliderOption implements Source<JSlider>, Option<Integer> {

    private JSlider slider;

    private String name;
    private int current;

    public NumberSliderOption(Integer low, Integer high, Integer initial, String name, final UpdateEvent<Integer> onUpdate) {
        slider = new JSlider(low, high, initial);
        slider.setMajorTickSpacing((high.intValue() - low.intValue()) / 7);
        slider.setPaintTicks(true);

        Hashtable label = new Hashtable();
        label.put((high.intValue() - low.intValue()) / 2, new JLabel(name));

        slider.setLabelTable(label);
        slider.setPaintLabels(true);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                onUpdate.onUpdate(slider.getValue());
                current = slider.getValue();
            }
        });

        this.name = name;

        onUpdate.onUpdate(initial);

        OptionDebugManager.addLiveOption(this);
    }

    public JSlider get() {
        return slider;
    }

    public String getDescription() {
        return name;
    }

    @Override
    public Integer getValue() {
        return current;
    }
}
