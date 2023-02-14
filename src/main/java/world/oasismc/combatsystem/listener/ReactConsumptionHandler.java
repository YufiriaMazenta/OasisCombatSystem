package world.oasismc.combatsystem.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import world.oasismc.combatsystem.event.vision.*;
import world.oasismc.combatsystem.vision.VisionManager;

public enum ReactConsumptionHandler implements Listener {

    INSTANCE;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onOverLoad(OverloadEvent event) {
        consumeReactedVision(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFireReact(FireReactEvent event) {
        consumeReactedVision(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvaporation(EvaporationEvent event) {
        consumeReactedVision(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMelt(MeltReactEvent event) {
        consumeReactedVision(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDiffusion(DiffusionEvent event) {
        consumeReactedVision(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCrystallized(CrystallizedEvent event) {
        consumeReactedVision(event);
    }

    public void consumeReactedVision(VisionReactEvent event) {
        VisionManager.addVision(event.getEntity(), event.getReactedVision(), - event.getReactedVisionNum());
    }

}
