package world.oasismc.combatsystem.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import world.oasismc.combatsystem.event.vision.EntityTrigVisionReactEvent;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionManager;

public enum EntityAttackHandler implements Listener {

    INSTANCE;

    @EventHandler
    public void trigVisionReact(EntityDamageByEntityEvent event) {
        var damager = event.getDamager();
        var entity = event.getEntity();
        VisionAttack attack = VisionManager.getEntityVisionAttack(damager, event.getDamage());
        if (attack == null)
            return;
        event.setDamage(EntityTrigVisionReactEvent.buildEvent(entity, event, attack).call().getDamage());
    }

}
