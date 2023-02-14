package world.oasismc.combatsystem.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.oasismc.combatsystem.event.vision.*;
import world.oasismc.combatsystem.util.NamespacedKeyUtil;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionManager;

public enum VisionEventHandler implements Listener {

    INSTANCE;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleVisionAttack(EntityTrigVisionReactEvent event) {
        Entity entity = event.getEntity();
        VisionAttack visionAttack = event.getVisionAttack();
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        PersistentDataContainer visionContainer = dataContainer.get(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER);

        if (visionContainer == null || visionContainer.isEmpty()) {
            switch (visionAttack.getType()) {
                case DENDRO, ELECTRO, HYDRO, CRYO, PYRO -> VisionAttachmentEvent.buildEvent(visionAttack, entity).call();
            }
        } else {
            event.setDamage(VisionManager.visionReaction(event));
        }
        //TODO 显示伤害数字
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVisionAttached(VisionAttachmentEvent event) {
        VisionAttack attack = event.getAttachAttack();
        VisionManager.addVision(event.getAttachedEntity(), attack.getType(), attack.getVisionNum());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onOverload(OverloadEvent event) {
        Entity entity = event.getEntity();
        entity.getLocation().createExplosion(event.getExplosionPower(), true, false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFireReact(FireReactEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setFireTicks(event.getFireTick());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvaporation(EvaporationEvent event) {
        //TODO 蒸发反应
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMelt(MeltReactEvent event) {
        //TODO 融化反应
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDiffusion(DiffusionEvent event) {
        //TODO 扩散反应
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCrystallized(CrystallizedEvent event) {
        //TODO 结晶反应
    }

}
