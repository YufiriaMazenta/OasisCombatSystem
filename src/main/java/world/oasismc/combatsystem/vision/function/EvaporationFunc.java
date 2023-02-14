package world.oasismc.combatsystem.vision.function;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

/**
 * 蒸发反应
 */
public enum EvaporationFunc implements VisionReactFunc {

    INSTANCE;

    @Override
    public Double execute(Entity entity, VisionAttack attack, Double damage) {
        VisionType triggerVision = attack.getType();
        double scale;
        if (triggerVision == VisionType.PYRO)
            scale = OasisCombatSystem.getInstance().getConfig().getDouble("evaporation_damage_scale.pyro_trigger", 1.5);
        else
            scale = OasisCombatSystem.getInstance().getConfig().getDouble("evaporation_damage_scale.hydro_trigger", 2.0);
        return scale * damage;
    }
}
