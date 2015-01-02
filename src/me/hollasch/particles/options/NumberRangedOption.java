package me.hollasch.particles.options;

import me.hollasch.particles.util.Range;
import me.hollasch.particles.util.slider.RangeSlider;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class NumberRangedOption implements Source<RangeSlider> {

    private RangeSlider slider;

    public NumberRangedOption(int low, int high, int initLow, int initHigh, String name, final UpdateEvent<Range> onUpdate) {
        slider = new RangeSlider(low, high);
        slider.setMajorTickSpacing((high - low) / 5);
        slider.setMinorTickSpacing((high - low) / 10);
        slider.setPaintTicks(true);

        slider.setValue(initLow);
        slider.setUpperValue(initHigh);

        Hashtable label = new Hashtable();
        label.put((high - low) / 2, new JLabel(name));

        slider.setLabelTable(label);
        slider.setPaintLabels(true);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                RangeSlider rs = (RangeSlider) e.getSource();
                int low = rs.getValue();
                int high = rs.getUpperValue();

                onUpdate.onUpdate(new Range(low, high));
            }
        });

        onUpdate.onUpdate(new Range(initLow, initHigh));
    }

    public RangeSlider get() {
        return slider;
    }
}
