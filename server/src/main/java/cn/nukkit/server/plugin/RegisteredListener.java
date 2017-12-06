package cn.nukkit.server.plugin;

import cn.nukkit.api.event.EventPriority;
import cn.nukkit.api.event.Listener;
import cn.nukkit.server.utils.EventException;
import co.aikar.timings.Timing;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class RegisteredListener {

    private final Listener listener;

    private final EventPriority priority;

    private final Plugin plugin;

    private final EventExecutor executor;

    private final boolean ignoreCancelled;

    private final Timing timing;

    public RegisteredListener(Listener listener, EventExecutor executor, EventPriority priority, Plugin plugin, boolean ignoreCancelled, Timing timing) {
        this.listener = listener;
        this.priority = priority;
        this.plugin = plugin;
        this.executor = executor;
        this.ignoreCancelled = ignoreCancelled;
        this.timing = timing;
    }

    public Listener getListener() {
        return listener;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public void callEvent(Event event) throws EventException {
        if (event instanceof Cancellable) {
            if (event.isCancelled() && isIgnoringCancelled()) {
                return;
            }
        }
        this.timing.startTiming();
        executor.execute(listener, event);
        this.timing.stopTiming();
    }

    public boolean isIgnoringCancelled() {
        return ignoreCancelled;
    }
}
