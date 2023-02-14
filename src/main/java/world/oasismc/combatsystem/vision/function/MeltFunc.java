package world.oasismc.combatsystem.vision.function;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.event.vision.VisionReactEvent;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

/**
 * 融化反应
 */
public enum MeltFunc implements VisionReactFunc {

    INSTANCE;

    @Override
    public Double execute(Entity entity, VisionAttack attack, Double damage) {
        VisionType triggerVision = attack.getType();
        double scale;
        if (triggerVision == VisionType.PYRO)
            scale = OasisCombatSystem.getInstance().getConfig().getDouble("melt_damage_scale.pyro_trigger", 2.0);
        else
            scale = OasisCombatSystem.getInstance().getConfig().getDouble("melt_damage_scale.cryo_trigger", 1.5);
        return scale * damage;
    }

}
