package world.oasismc.combatsystem.vision.function;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionAttack;

/**
 * 超载反应
 */
public enum OverloadFunc implements VisionReactFunc {

    INSTANCE;

    @Override
    public Double execute(Entity entity, VisionAttack attack, Double damage) {
        double explosionPowerScale = OasisCombatSystem.getInstance().getConfig().getDouble("overload_explosion_power_scale", 2.0);
        entity.getLocation().createExplosion((float) (attack.getVisionNum() * explosionPowerScale), true, false);
        return damage;
    }

}
