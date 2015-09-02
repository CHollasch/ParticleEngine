package me.hollasch.particles.options;

import me.hollasch.particles.debug.OptionDebugManager;

import javax.swing.*;

public abstract class SimpleJOption<K extends JComponent, V> implements Source<K>, Option<V> {

    protected K component;
    protected V active;

    private UpdateEvent<V> updateEvent;
    protected String optionName;

    public SimpleJOption(V initialValue, String name, final UpdateEvent<V> onUpdate) {
        this.active = initialValue;
        this.optionName = name;

        this.updateEvent = onUpdate;
        this.updateEvent.onUpdate(initialValue);

        OptionDebugManager.addLiveOption(this);
    }

    public abstract K createComponent();

    protected void changeOptionValue(V current) {
        this.active = current;
        updateEvent.onUpdate(current);
    }

    @Override
    public K get() {
        if (component == null) {
            this.component = createComponent();
        }
        return component;
    }

    @Override
    public String getDescription() {
        return optionName;
    }

    @Override
    public V getValue() {
        return active;
    }
}
