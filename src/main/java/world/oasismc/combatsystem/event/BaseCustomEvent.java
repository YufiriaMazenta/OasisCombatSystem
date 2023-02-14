package world.oasismc.combatsystem.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BaseCustomEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public BaseCustomEvent(boolean async) {
        super(async);
    }

    public BaseCustomEvent() {
        this(false);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public BaseCustomEvent call() {
        Bukkit.getPluginManager().callEvent(this);
        return this;
    }

}
