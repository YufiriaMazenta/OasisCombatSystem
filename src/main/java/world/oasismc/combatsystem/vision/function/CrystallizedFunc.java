package world.oasismc.combatsystem.vision.function;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.vision.VisionAttack;

/**
 * 结晶反应
 */
public enum CrystallizedFunc implements VisionReactFunc {

    INSTANCE;

    @Override
    public Double execute(Entity entity, VisionAttack attack, Double damage) {
        //TODO
        return damage;
    }

}
