package world.oasismc.combatsystem.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import world.oasismc.combatsystem.vision.VisionManager;

public enum PlayerDeathListener implements Listener {

    INSTANCE;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        VisionManager.clearVision(event.getEntity());
    }

}
