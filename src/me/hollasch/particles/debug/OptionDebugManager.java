package me.hollasch.particles.debug;

import me.hollasch.particles.options.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Hollasch
 * @since 1/2/2015
 */
public class OptionDebugManager {

    private static List<Option> live_options = new ArrayList<Option>();

    public static void addLiveOption(Option opt) {
        live_options.add(opt);
    }

    public static List<Option> getAllOptions() {
        return live_options;
    }
}
