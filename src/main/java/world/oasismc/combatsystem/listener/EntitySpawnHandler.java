package world.oasismc.combatsystem.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import world.oasismc.combatsystem.util.LangUtil;
import world.oasismc.combatsystem.vision.VisionManager;
import world.oasismc.combatsystem.vision.VisionType;

import java.util.*;
import java.util.function.Supplier;

public enum EntitySpawnHandler implements Listener {

    INSTANCE;

    private Map<EntityType, Supplier<VisionType>> defEntityVisionTypeMap;

    EntitySpawnHandler() {
        defEntityVisionTypeMap = new HashMap<>();
        defEntityVisionTypeMap.put(EntityType.FALLING_BLOCK, () -> VisionType.GEO);
        defEntityVisionTypeMap.put(EntityType.LIGHTNING, () -> VisionType.ELECTRO);
        defEntityVisionTypeMap.put(EntityType.BLAZE, () -> VisionType.PYRO);
        defEntityVisionTypeMap.put(EntityType.MAGMA_CUBE, () -> VisionType.PYRO);
        defEntityVisionTypeMap.put(EntityType.PRIMED_TNT, () -> VisionType.PYRO);
        defEntityVisionTypeMap.put(EntityType.FIREBALL, () -> VisionType.PYRO);
        defEntityVisionTypeMap.put(EntityType.ALLAY, () -> VisionType.CRYO);
        defEntityVisionTypeMap.put(EntityType.GUARDIAN, () -> VisionType.HYDRO);
        defEntityVisionTypeMap.put(EntityType.ELDER_GUARDIAN, () -> VisionType.HYDRO);

        defEntityVisionTypeMap.put(EntityType.ZOMBIE, () -> VisionType.DENDRO);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        String baseEntityName = LangUtil.lang("entity_name." + entity.getType().name(), entity.getType().name());
        VisionType vision = defEntityVisionTypeMap.getOrDefault(entity.getType(), () -> null).get();
        String entityDisplayFormat = LangUtil.lang("entity_display_format", "<vision> &r<entity_name>");
        String visionStr;
        if (vision != null) {
            String icon = vision.getIcon();
            visionStr = icon + " ";
            VisionManager.setAttackVision(entity, vision, 1.0);
            VisionManager.addVision(entity, vision, (double) Double.MAX_EXPONENT, true);
        } else {
            visionStr = "";
        }

        if (entity instanceof LivingEntity) {
            entityDisplayFormat = entityDisplayFormat.replace("<vision>", visionStr);
            entityDisplayFormat = entityDisplayFormat.replace("<entity_name>", baseEntityName);
            entity.setCustomName(LangUtil.color(entityDisplayFormat));
            entity.setCustomNameVisible(true);
        }
    }

}
