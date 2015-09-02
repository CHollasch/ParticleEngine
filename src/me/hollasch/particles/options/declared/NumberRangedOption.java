package me.hollasch.particles.options.declared;

import me.hollasch.particles.options.SimpleJOption;
import me.hollasch.particles.options.UpdateEvent;
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
public class NumberRangedOption extends SimpleJOption<RangeSlider, Range> {

    private int minimum;
    private int maximum;

    public NumberRangedOption(int low, int high, int initLow, int initHigh, String name, final UpdateEvent<Range> onUpdate) {
        super(new Range(initLow, initHigh), name, onUpdate);

        this.minimum = low;
        this.maximum = high;
    }

    @Override
    public RangeSlider createComponent() {
        RangeSlider slider = new RangeSlider(minimum, maximum);
        slider.setMajorTickSpacing((maximum - minimum) / 10);
        slider.setPaintTicks(true);

        slider.setValue((int) getValue().getMinimum());
        slider.setUpperValue((int) getValue().getMaximum());

        Hashtable<Integer, JLabel> sliderLabel = new Hashtable<Integer, JLabel>();
        sliderLabel.put((maximum - minimum) / 2, new JLabel(getDescription()));

        slider.setLabelTable(sliderLabel);
        slider.setPaintLabels(true);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                RangeSlider rangeSlider = (RangeSlider) e.getSource();
                changeOptionValue(new Range(rangeSlider.getValue(), rangeSlider.getUpperValue()));
            }
        });

        return slider;
    }
}
