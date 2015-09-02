package me.hollasch.particles.options.declared;

import me.hollasch.particles.options.SimpleJOption;
import me.hollasch.particles.options.UpdateEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class NumberSliderOption extends SimpleJOption<JSlider, Integer> {

    private int low;
    private int high;

    public NumberSliderOption(int low, int high, int initial, String name, UpdateEvent<Integer> onUpdate) {
        super(initial, name, onUpdate);

        this.low = low;
        this.high = high;
    }

    @Override
    public JSlider createComponent() {
        JSlider slider = new JSlider(low, high, getValue());
        slider.setMajorTickSpacing((high - low) / 7);
        slider.setPaintTicks(true);

        Hashtable<Integer, JLabel> sliderLabel = new Hashtable<Integer, JLabel>();
        sliderLabel.put((high - low) / 2, new JLabel(getDescription()));

        slider.setLabelTable(sliderLabel);
        slider.setPaintLabels(true);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                changeOptionValue(((JSlider) e.getSource()).getValue());
            }
        });

        return slider;
    }
}
