package world.oasismc.combatsystem.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.oasismc.combatsystem.util.NamespacedKeyUtil;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionManager;
import world.oasismc.combatsystem.event.vision.EntityTrigVisionReactEvent;

public enum EntityAttackListener implements Listener {

    INSTANCE;

    @EventHandler
    public void fireVisionDamageEvent(EntityDamageByEntityEvent event) {
        var damager = event.getDamager();
        var entity = event.getEntity();
        VisionAttack attack = VisionManager.getEntityVisionAttack(damager);
        if (attack == null)
            return;
        event.setDamage(EntityTrigVisionReactEvent.buildEvent(entity, event, attack).call().getDamage());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleVisionAttack(EntityTrigVisionReactEvent event) {
        Entity entity = event.getEntity();
        VisionAttack visionAttack = event.getVisionAttack();
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        PersistentDataContainer visionContainer = dataContainer.get(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER);

        if (visionContainer == null || visionContainer.isEmpty()) {
            switch (visionAttack.getType()) {
                case DENDRO, ELECTRO, HYDRO, CRYO, PYRO -> VisionManager.addVision(entity, visionAttack.getType(), visionAttack.getVisionNum());
            }
        } else {
            event.setDamage(VisionManager.visionReaction(event));
        }
    }

}
